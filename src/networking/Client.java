package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.ws.soap.Addressing;

import logic.Match;
import logic.Ticket;

public class Client {

	ArrayList<Match> matches;
	Socket socket;
	Scanner userInput;
	Ticket ticket;

	ObjectInputStream in;
	ObjectOutputStream out;
	
	boolean isActive = true;

	public static void main (String[] args){

		new Client();

	}

	public void showMatchList(){
		ticket = new Ticket();
		int counter = 0;
		for(Match match : matches)
			System.out.println(++counter+". "+match + "\n");
	}
	
	public void addMatchToTicket(){
		System.out.println("Select a game !\n");
		
		while(!userInput.hasNext())
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int input = userInput.nextInt();
		if(matches.get(input - 1).isOver()){
			System.out.println("Sorry, you cannot bet on this match.\n");
			return;
		}
		ticket.addGameToTicket(matches.get(input-1));
		System.out.println("1 X 2\n");
		while(!userInput.hasNext())
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		char opt = userInput.next().charAt(0);
		switch(opt){
		case '1':
			matches.get(input - 1).setTeam(matches.get(input - 1).getTeamA());
			break;
		case '2':
			matches.get(input - 1).setTeam(matches.get(input - 1).getTeamB());
			break;
		case 'x':
			matches.get(input - 1).setTeam("draw");
			break;
		}
		matches.remove(matches.get(input-1));
		System.out.println("Ticket updated!\nYour ticket: \n"+ticket);
	}
	
	public void displayMenu(){
		System.out.println(""
				+ "1) View game list\n"
				+ "2) Add game to your ticket\n"
				+ "3) Validate ticket\n"
				+ "4) Load ticket\n");
	}

	public Client(){
		matches = new ArrayList<Match> ();
		userInput = new Scanner(System.in);
		try {
			socket = new Socket("localhost",4444);
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			getMatchList();	
			while(isActive){
				displayMenu();
				processUserInput();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void processUserInput() {
		while(!userInput.hasNext())
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int input = userInput.nextInt();
		try {
			out.writeInt(input);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch(input){
		case 1:
			showMatchList();
			break;
		case 2:
			showMatchList();
			addMatchToTicket();
			break;
		case 3:
			if(ticket != null && !ticket.isValidated())
			validated();
			break;
		case 4:
			loadTicket();
			break;
		}

	}

	private void loadTicket() {
		System.out.print("Enter ticket ID: ");
		while(!userInput.hasNext())
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int input = userInput.nextInt();
		
		try {
			out.writeInt(input);
			out.flush();
			boolean found = in.readBoolean();
			if(found)
				System.out.println((Ticket)in.readObject());
			else System.out.println(in.readObject());
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private void validated() {
		if(ticket.getSum() == 0){
		float sum;
		System.out.println("Place sum to bet: ");
		sum = userInput.nextFloat();
		ticket.validate(sum);
		}
		try {
			out.writeObject(ticket);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void getMatchList(){
		try {
			matches = (ArrayList <Match>) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}




