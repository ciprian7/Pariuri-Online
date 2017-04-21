package logic;

public class Game {
	
	String teamA, teamB;
	int scoreA, scoreB;
	float stakeA, stakeB;
	
	Game(String teamA, float stakeA, String teamB, float stakeB){
		this.teamA = teamA;
		this.stakeA = stakeA;
		this.teamB = teamB;
		this.stakeB = stakeB;
		this.scoreA = 0;
		this.scoreB = 0;
	}

}
