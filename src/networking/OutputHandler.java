package networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import logic.Match;

public class OutputHandler extends Thread{
	ObjectOutputStream out;
	Socket socket;
	ServerConnection serverConnection;
	boolean isActive = true;
	boolean isWorking = false;
	
	public OutputHandler(ServerConnection serverConnection, Socket socket){
		this.serverConnection = serverConnection;
		this.socket = socket;
	}
	
	public void sendListToClient(){
		try {
			System.out.println("sending game list...");
			out.writeInt(serverConnection.getServer().getMatches().size());
			for(Match match : serverConnection.getServer().getMatches()){
				match.setWinningTeam();
				out.writeObject(match.getTeamA());
				out.writeFloat(match.getStakeA());
				out.writeObject(match.getTeamB());
				out.writeFloat(match.getStakeB());
				out.writeInt(match.getScoreA());
				out.writeInt(match.getScoreB());
				out.writeInt(match.getHour());
				out.writeInt(match.getMinute());
				out.writeObject(match.getUserTeam());
				out.writeBoolean(match.isOver());
			}
			out.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			sendListToClient();
			while(isActive){
				;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void sendTicketInfo(int requestedID) {
		try {
			out.writeObject(serverConnection.getServer().getTicketById(requestedID));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
