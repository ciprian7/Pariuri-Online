package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import logic.Game;

public class Client {

	private Socket socket;
	private String username;
	private String password;

	BufferedReader in;
	PrintWriter out;

	public static void main (String[] args){

		new Client();

	}

	public Client(){
		try {
			socket = new Socket("localhost",4567);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());


			listenForInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listenForInput(){
		Scanner clientInput = new Scanner(System.in);

		while(true){
			while(!clientInput.hasNextLine())
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String message = clientInput.nextLine();

			out.println(message);
			out.flush();
			if(message.equals("disconnect"))
				break;
		}

		try {
			in.close();
			out.close();
			socket.close();
			clientInput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


}
