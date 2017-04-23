package logic;

import java.io.Serializable;
import java.util.Date;



public class Match implements Serializable{


	private static int serialVersionUID = 1; 
	String teamA, teamB, winningTeam="winningTeam", userTeam ="userTeam";
	int scoreA, scoreB, hour, minute;
	float stakeA, stakeB, stakeDraw = 1;
	boolean isOver = false;
	int id;

	
	Date date = new Date();
	public Match(String teamA, float stakeA, String teamB, float stakeB, int hours, int minutes){
		this.teamA = teamA;
		this.stakeA = stakeA;
		this.teamB = teamB;
		this.stakeB = stakeB;
		this.scoreA = 0;
		this.scoreB = 0;
		this.hour = hours;
		this.minute = minutes;
		date.setHours(hours);
		date.setMinutes(minutes);
		id = createID();
	}
	public synchronized int createID() {
		return serialVersionUID++;
	}
	public Match(String teamA, float stakeA, String teamB, float stakeB,int scoreA, int scoreB,int hours, int minutes,String userTeam, boolean isOver){
		this.teamA = teamA;
		this.stakeA = stakeA;
		this.teamB = teamB;
		this.stakeB = stakeB;
		this.scoreA = scoreA;
		this.scoreB = scoreB;
		this.hour = hours;
		this.minute = minutes;
		this.isOver = isOver;
		this.userTeam = userTeam;
		this.winningTeam = winningTeam;
		date.setHours(hours);
		date.setMinutes(minutes);
	}
	
	public void setOver(){
		this.isOver = true;
	}
	
	@Override
	public String toString() {
		if(userTeam.equals(teamA))
		return 	"[x] "+teamA + " \t-\t" + teamB + "\n";
		else if(userTeam.equals(teamB)) 
			return teamA + "\t"  + teamB +"[x]"+"\n";
		else
			return 	teamA + "\t" + "[x]" + teamB +"\n";
	}
																																										
	public float getStake() {
		if(userTeam.equals(teamA))
			return stakeA;
		else if(userTeam.equals(teamB))
			return stakeB;
		return stakeDraw;
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
			this.winningTeam = teamA;
		else if(scoreB > scoreA)
			this.winningTeam = teamB;
		else this.winningTeam = "draw";
		
		System.out.println(this.winningTeam);
	}
	public boolean won() {
		setWinningTeam();
		return userTeam.equals(winningTeam);
	}

	public boolean isOver() {
		setWinningTeam();
		return isOver;
	}
	

	public void setScore(int scoreA, int scoreB) {
		this.scoreA = scoreA;
		this.scoreB = scoreB;
		
	}

	public int getScoreA() {
		return scoreA;
	}
	public int getScoreB() {
		return scoreB;
	}

	public float getStakeA() {
		return stakeA;
	}
	

	public float getStakeB() {
		return stakeB;
	}

	public int getMinute() {
		return minute;
	}
	
	public int getHour(){
		return hour;
	}
	public String getWinningTeam() {
		return this.winningTeam;
	}
	
	public String getUserTeam(){
		return this.userTeam;
	}
		
	
	public boolean equals(Match match){
		return this.getTeamA().equals(match.getTeamA()) && this.getTeamB().equals(match.getTeamB());
	}
}
