package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import logic.Ticket;

public class ServerConnection extends Thread {

	Socket socket;
	Server server;
	ObjectInputStream in;
	ObjectOutputStream out;
	boolean isActive = true;
	
	ArrayList <Ticket> tickets;

	public ServerConnection(Socket socket, Server server){
		super("ServerConnectionThread");
		tickets = new ArrayList<Ticket>();
		this.socket = socket;
		this.server = server;
	}

	public void sendListToClients(){
		for(int i = 0 ; i < server.connections.size() ; ++i){
			ServerConnection serverConnection = server.connections.get(i);
			serverConnection.sendListToClient();
		}
	}

	public void sendListToClient(){
		try {

			out.writeObject(server.getMatches());
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public void run(){
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			sendListToClient();
			
			while(isActive){
				while(in.available() == 0)
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				int input = in.readInt();
				if(input == 3)
				getTicket();
				else if(input == 4)
					sendTicketInfo();
			}
			
			in.close();
			out.reset();
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	private void sendTicketInfo() {
		try {
			int ticketID = in.readInt();
			getTicketById(ticketID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void getTicketById(int ticketID) {
		boolean found = false;
		Ticket t = new Ticket();
		for(Ticket ticket : tickets)
			if(ticket.getID() == ticketID){
				t = ticket;
				found = true;
			}
		try {
			out.writeBoolean(found);
			if(found)
				out.writeObject(t);
			else
				out.writeObject("Invalid ticked ID!\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	private void getTicket() {
		Ticket ticket;
		try {
			tickets.add((Ticket)in.readObject());
			System.out.println("New ticket received: "+tickets.get(tickets.size()-1));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
