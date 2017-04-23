package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import logic.Match;
import logic.Ticket;

public class ServerConnection extends Thread {

	Socket socket;
	Server server;

	boolean isActive = true;
	InputHandler in;
	OutputHandler out;

	ArrayList <Ticket> tickets;

	public ServerConnection(Socket socket, Server server){
		super("ServerConnectionThread");
		tickets = new ArrayList<Ticket>();
		this.socket = socket;
		this.server = server;
	}




	public void run(){
		in = new InputHandler(this,socket);
		out = new OutputHandler(this,socket);
		in.start();
		out.start();

	}


	public Server getServer(){
		return server;
	}

	public OutputHandler getOutputHandler(){
		return out;
	}
/*	private void sendTicketInfo() {
		try {
			int ticketID = in.readInt();
			getTicketById(ticketID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
	/*
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
	*/

}
