package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import logic.*;

public class Server {

	ServerSocket serverSocket;
	Socket socket;


	BufferedReader in;
	PrintWriter out;
	
	ArrayList <Game> games;
	ArrayList <Ticket> tickets;

	public static void main(String[] args){

		new Server();
	}

	public Server(){
		games = new ArrayList <Game>();
		tickets = new ArrayList <Ticket>();
		try {
			serverSocket = new ServerSocket(4567);
			Socket socket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			listenForData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listenForData(){
		
		String messageReceived;
		while(true){
			
			try {
				messageReceived = in.readLine();
				System.out.println("<client>: "+messageReceived);
				if(messageReceived.equals("disconnect"))
					break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			in.close();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

