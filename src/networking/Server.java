package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import logic.*;

public class Server {

	ServerSocket serverSocket;
	public ArrayList <ServerConnection> connections;
	ObjectInputStream in;
	ObjectOutputStream out;


	ArrayList <Match> matches;
	ArrayList <Ticket> tickets;
	
	boolean isActive = true;

	public static void main(String[] args){

		new Server();
	}
	
	
	public Server(){
		connections = new ArrayList<ServerConnection> ();
		matches = new ArrayList <Match>();
		tickets = new ArrayList <Ticket>();
		
		matches.add(new Match("FC Barcelona",2,"Juventus",20,21,45));
		matches.add(new Match("Real Madrid",2,"Bayern",20,21,45));
		
		matches.get(1).setOver();
		
			try {
				serverSocket = new ServerSocket(4444);
				while(isActive){
				
				Socket socket = serverSocket.accept();
				System.out.println("new connection");

				ServerConnection serverConnection = new ServerConnection(socket, this);
				serverConnection.start();
				connections.add(serverConnection);
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}
}

