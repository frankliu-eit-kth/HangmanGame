package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.StringJoiner;

import common.Constants;
import common.MessageException;
import common.MsgType;
import server.controller.GameController;

public class ClientHandler implements Runnable{
	private final GameServer server;
    private final Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private boolean connected;
    private GameController gameController;
    
    ClientHandler(GameServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.gameController=new GameController();
        gameController.newGame(clientSocket.getInetAddress().toString());
        connected = true;
    }
    @Override
    public void run() {
        try {
            boolean autoFlush = true;
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        while (connected) {
            try {
                Message msg = new Message(fromClient.readLine());
                switch (msg.msgType) {
                    case USER:
                        String newName = msg.msgBody;
                        sendMsg(this.gameController.changeUserName(newName));
                        break;
                    case START:
                    	sendMsg(this.gameController.start());
                    	break;
                    case USER_INPUT:
                    	String input=msg.msgBody;
                    	sendMsg(this.gameController.executeRound(input));
                        break;
                    case DISCONNECT:
                        disconnectClient();
                        break;
                    default:
                        throw new MessageException("Received corrupt message: " + msg.receivedString);
                }
            } catch (IOException ioe) {
                disconnectClient();
                throw new MessageException(ioe);
            }
        }
    }
    
    void sendMsg(String msg) {
        StringJoiner joiner = new StringJoiner(Constants.MSG_DELIMETER);
        joiner.add(MsgType.SERVERMSG.toString());
        joiner.add(msg);
        String fullMessage=joiner.toString();
        int fullMessageLength=fullMessage.length();
        StringJoiner headerJoiner = new StringJoiner(Constants.MSG_DELIMETER);
        headerJoiner.add(new String(""+fullMessageLength));
        headerJoiner.add(fullMessage);
        toClient.println(headerJoiner.toString());
    }

    
    private void disconnectClient() {
        try {
            clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        connected = false;
        server.removeHandler(this);
    }
    
    
    
    private static class Message {
        private MsgType msgType;
        private String msgBody;
        private String receivedString;

        private Message(String receivedString) {
        	checkComplete(receivedString);
            parse(receivedString);
            this.receivedString = receivedString;
        }
        
        private void checkComplete(String receivedString) {
        	String[] msgTokens = receivedString.split(Constants.MSG_DELIMETER);
            int lengthHeader=Integer.parseInt(msgTokens[Constants.MSG_LENGTH_INDEX]);
            int msgLength=receivedString.length()-msgTokens[Constants.MSG_LENGTH_INDEX].length()-Constants.MSG_DELIMETER.length();
            if(lengthHeader!=msgLength) {
            	throw new MessageException("Received incomplete message: " + receivedString);
            }else {
            	//for test
            	System.out.println("received complete message from client");
            }
        }
        private void parse(String strToParse) {
            try {
                String[] msgTokens = strToParse.split(Constants.MSG_DELIMETER);
                msgType = MsgType.valueOf(msgTokens[Constants.MSG_TYPE_INDEX].toUpperCase());
                if (hasBody(msgTokens)) {
                    msgBody = msgTokens[Constants.MSG_BODY_INDEX];
                }
            } catch (Throwable throwable) {
                throw new MessageException(throwable);
            }
        }
        
        private boolean hasBody(String[] msgTokens) {
            return msgTokens.length > 2;
        }
    }
    
}
