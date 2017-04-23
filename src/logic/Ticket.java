package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Ticket implements Serializable{

	private static int serialVersionUID = 1; 
	ArrayList <Match> matches;
	float stake;
	float sum;
	int id;
	boolean validated;
	boolean winningTicket = false;
	public Ticket(){
		matches = new ArrayList <Match> ();
		sum = 0;
		stake = 1;
		id = createID();
	}
	
	
	public synchronized int createID() {
		return serialVersionUID++;
	}

	public void addMatchToTicket(Match match){
		matches.add(match);
	}

	public void setSum(float sum){
		this.sum = sum;

	}


	public float getWinSum(){
		for(Match match : matches)
			stake *= match.getStake();
		return stake*sum;
	}

	public String toString(){
		String output = "Bet id "+id + "\n";
		for(Match match : matches)
			output += match;
		output += "You bet: " + sum + "\tPotential win: " + getWinSum();
		return output+"\n";
	}

	public boolean isValidated(){
		return validated;
	}
	
	public void validate() {
			validated = true;
			boolean won = true;
			for(Match match : matches)
				if(!match.won())
					won = false;
			winningTicket = won;
	}

	public int getID() {
		return id;
	}

	public float getSum() {
		return sum;
	}

	public boolean contains(Match match) {
		for(Match m : matches)
			if(m.getTeamA().equals(match.getTeamA()))
				return true;
		return false;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}


	public void reset() {
		matches.clear();
		
	}



}
