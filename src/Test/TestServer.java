package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestServer {
	private static PrintWriter toServer;
	private static BufferedReader fromServer;
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Socket clientSocket=new Socket();
		clientSocket.connect(new InetSocketAddress("localhost",8080));
		boolean autoFlush = true;
		toServer = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
        fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        toServer.println("START");
        System.out.println(fromServer.readLine());
        toServer.println("USER_INPUT##a");
        System.out.println(fromServer.readLine());
		while(true) {
			System.out.println(fromServer.readLine());
		}
		
        
		
	}

}
