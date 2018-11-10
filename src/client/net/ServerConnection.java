package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.StringJoiner;

import common.Constants;
import common.MessageException;
import common.MsgType;

public class ServerConnection {
	private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
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
    private void sendMsg(String... parts) {
        StringJoiner joiner = new StringJoiner(Constants.MSG_DELIMETER);
        for (String part : parts) {
            joiner.add(part);
        }
        toServer.println(joiner.toString());
    }
    public void sendInput(String input) {
    	sendMsg(MsgType.USER_INPUT.toString(), input);
    }
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
            String[] msgParts = entireMsg.split(Constants.MSG_DELIMETER);
            if (MsgType.valueOf(msgParts[Constants.MSG_TYPE_INDEX].toUpperCase()) != MsgType.SERVERMSG) {
                throw new MessageException("Received corrupt message: " + entireMsg);
            }
            return msgParts[Constants.MSG_BODY_INDEX];
        }
    }
}
