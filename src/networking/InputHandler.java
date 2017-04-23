package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import logic.Match;
import logic.Ticket;

public class InputHandler  extends Thread{

	private Socket socket;
	private ServerConnection serverConnection;
	private ObjectInputStream in;
	private boolean isActive = true;
	public boolean isWorking;
	public InputHandler(ServerConnection serverConnection, Socket socket){
		this.serverConnection = serverConnection;
		this.socket = socket;
	}



	@Override
	public void run() {
		int input;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			while(isActive){
				while(in.available() == 0)
					sleep();
				int request = in.readInt();
				handleRequest(request);
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void handleRequest(int request) {
		switch(request){
		case 1:
			serverConnection.getOutputHandler().sendListToClient();
			break;
		case 2:
			try {
				Ticket ticket = new Ticket();
				int count = in.readInt();
				for(int i = 0 ; i < count ; ++i){
					String teamA = (String) in.readObject();
					float stakeA = (Float)  in.readFloat();
					String teamB = (String) in.readObject();
					float stakeB = (Float)  in.readFloat();
					int scoreA = in.readInt();
					int scoreB = in.readInt();
					int hour = in.readInt();
					int minute = in.readInt();
					String userTeam = (String)in.readObject();
					boolean isOver = in.readBoolean();
					ticket.addMatchToTicket(new Match(teamA,stakeA,teamB,stakeB,scoreA,scoreB,hour,minute,userTeam,isOver));
				}
				serverConnection.getServer().getTickets().add(ticket);
				serverConnection.getServer().getServerDisplay().refresh();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			try {

				serverConnection.getOutputHandler().sendTicketInfo(in.readInt());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}

		
	}


	public void sleep(){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void getTicket() {
		try {
			serverConnection.tickets.add((Ticket)in.readObject());		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
