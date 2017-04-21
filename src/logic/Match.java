package logic;

import java.io.Serializable;
import java.util.Date;



public class Match implements Serializable{

	String teamA, teamB, winningTeam, userTeam;
	int scoreA, scoreB, hour, minute;
	float stakeA, stakeB;
	boolean isOver = false;
	
	Date date = new Date();
	public Match(String teamA, float stakeA, String teamB, float stakeB, int hours, int minutes){
		this.teamA = teamA;
		this.stakeA = stakeA;
		this.teamB = teamB;
		this.stakeB = stakeB;
		this.scoreA = 0;
		this.scoreB = 0;
		this.userTeam = "";
		date.setHours(hours);
		date.setMinutes(minutes);
	}
	
	public void setOver(){
		this.isOver = true;
	}
	
	@Override
	public String toString() {
		if(userTeam.equals(teamA))
		return 	"[x] "+teamA + "\t - \t " + teamB + "\n" +
				"\t"+stakeA + "\t    \t" + stakeB + "\nDate: " + date + "\n";
		else if(userTeam.equals(teamB)) 
			return 	teamA + "\t - \t " + teamB +"[x]" +
		"\t"+stakeA + "\t    \t" + stakeB + "\n" + date + "\n";
		else
			return 	teamA + "\t - \t " + teamB +"\n" +
			"\t"+stakeA + "\t    \t" + stakeB+ "\nDate: " + date + "\n";
	}

	public float getStake() {
		if(userTeam.equals(teamA))
			return stakeA;
		return stakeB;
	}
	
	public void setTeam(String userTeam){
		this.userTeam = userTeam;
	}
	
	public String getTeamA(){
		return teamA;
	}
	
	public String getTeamB(){
		return teamB;
	}
	
	public void setWinningTeam(){
		if(scoreA > scoreB)
			winningTeam = teamA;
		else if(scoreB > scoreA)
			winningTeam = teamB;
		else winningTeam = "draw";
	}
	public boolean won() {
		setWinningTeam();
		return userTeam.equals(winningTeam);
	}

	public boolean isOver() {
		return isOver;
	}
	
	public boolean equals(Match match){
		return true;
	}

}
