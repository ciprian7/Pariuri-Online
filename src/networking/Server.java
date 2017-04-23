package networking;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import display.ClientDisplay;
import display.Display;
import display.ServerDisplay;
import logic.*;

public class Server {

	ServerSocket serverSocket;
	public ArrayList <ServerConnection> connections;
	private Display display;
	private static ServerDisplay serverDisplay;
	


	static ArrayList <Match> matches;
	ArrayList <Ticket> tickets;
	
	boolean isActive = true;

	public static void main(String[] args){
		
		new Server();
		
	}
	
	
	public Server(){

		connections = new ArrayList<ServerConnection> ();
		matches = new ArrayList <Match>();
		tickets = new ArrayList <Ticket>();

		matches.add(new Match("FC Barcelona",2,"Real Madrid",20,22,0));
		matches.add(new Match("Lyon",2,"Monaco",20,21,45));
		matches.add(new Match("Academia Clinceni",2,"Dunnarea Calarasi",20,21,45));
		matches.get(0).setOver();

		tickets.add(new Ticket());
		tickets.add(new Ticket());
		serverDisplay = new ServerDisplay(this);
		serverDisplay.refresh();


		
			try {
				serverSocket = new ServerSocket(4444);
				while(isActive){

				Socket socket = serverSocket.accept();

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


	public ArrayList<Ticket> getTickets() {
		return tickets;

	}


	public ServerDisplay getServerDisplay() {	
		return serverDisplay;
	}


	public Ticket getTicketById(int requestedID) {
		for(int i = 0 ; i < tickets.size() ; ++i)
			if(tickets.get(i).getID() == requestedID)
				return tickets.get(i);
		return null;
	}

}

