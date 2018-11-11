package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.StringJoiner;

import common.GlobalConstants;
import common.MessageException;
import common.MsgType;

/*
 * @role: provides network operations for the upper level
 * 
 * @methods provide to the view:
 * 	connect(): connect to the server and if succeed create a new listener to listen the message
 * 	disconnect(): send a DISCONNECT message to server and then close the socket, the listener thread will naturally die
 * 	sendUsername()
 * 	sendStart()
 * 	sendInput() 
 */
public class ServerConnection {
	/*
	 * @role: timeout params
	 */
	private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    /*
     * @role:the socket used for connection
     */
    private Socket socket;
    /*
     * @role: io streams for communication with the server
     * @futhermore: hereby use the PrintWriter and BufferedReader to read text, and there are a lot kinds of other streams could be chosen from
     */
    private PrintWriter toServer;
    private BufferedReader fromServer;
    /*
     * @role: flag used to maintain the life of a thread
     */
    private volatile boolean connected;
  
    public void connect(String host, int port, OutputHandler broadcastHandler) throws
    IOException {
    	 socket = new Socket();
         socket.connect(new InetSocketAddress(host, port), TIMEOUT_HALF_MINUTE);
         socket.setSoTimeout(TIMEOUT_HALF_HOUR);
         connected = true;
         boolean autoFlush = true;
         toServer = new PrintWriter(socket.getOutputStream(), autoFlush);
         fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         new Thread(new Listener(broadcastHandler)).start();
    }
    
    public void disconnect() throws IOException {
        sendMsg(MsgType.DISCONNECT.toString());
        socket.close();
        socket = null;
        connected = false;
    }
    
    public void sendUsername(String username) {
        sendMsg(MsgType.USER.toString(), username);
    }
    
    public void sendStart() {
        sendMsg(MsgType.START.toString());
    }
    
    public void sendInput(String input) {
    	sendMsg(MsgType.USER_INPUT.toString(), input);
    }
    
    
    /*	
     * @steps: first join the parts to a String-> calculate the length-> join the length header and the original message to a new string-> send 
     * @warning: not very robust: length header position may change
     */
    private void sendMsg(String... parts) {
        StringJoiner msgJoiner = new StringJoiner(GlobalConstants.MSG_DELIMETER);
        for (String part : parts) {
            msgJoiner.add(part);
        }
        String msg=msgJoiner.toString();
        StringJoiner streamMsgJoiner=new StringJoiner(GlobalConstants.MSG_DELIMETER);
        streamMsgJoiner.add(new String(""+msg.length()));
        streamMsgJoiner.add(msg);
        toServer.println(streamMsgJoiner.toString());
    }
    
    /*
     * @role: a thread used to listen to the SERVERMSG messages
     * @functions:
     * 		1.read message from server
     * 		2.check the completeness of message by checking the length header
     * 		3.extract message body
     * 		4.pass the message body to output handler (which will pass the message to the view for further handle)
     * @life: will die when the socket closes
     */
    private class Listener implements Runnable {
        private final OutputHandler outputHandler;

        private Listener(OutputHandler outputHandler) {
            this.outputHandler = outputHandler;
        }

        @Override
        public void run() {
            try {
                for (;;) {
                    outputHandler.handleMsg(extractMsgBody(fromServer.readLine()));
                }
            } catch (Throwable connectionFailure) {
                if (connected) {
                    outputHandler.handleMsg("Lost connection.");
                }
            }
        }

        private String extractMsgBody(String entireMsg) {
            String[] msgParts = entireMsg.split(GlobalConstants.MSG_DELIMETER);
            int lengthHeader=Integer.parseInt(msgParts[GlobalConstants.MSG_LENGTH_INDEX]);
            int msgLength=entireMsg.length()-msgParts[GlobalConstants.MSG_LENGTH_INDEX].length()-GlobalConstants.MSG_DELIMETER.length();
            if(lengthHeader!=msgLength) {
            	throw new MessageException("Received incomplete message: " + entireMsg);
            }
            if (MsgType.valueOf(msgParts[GlobalConstants.MSG_TYPE_INDEX].toUpperCase()) != MsgType.SERVERMSG) {
                throw new MessageException("Received corrupt message: " + entireMsg);
            }
            return msgParts[GlobalConstants.MSG_BODY_INDEX];
        }
    }
}
