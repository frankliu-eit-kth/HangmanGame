package client.view;

public class GameStatus {
	private static final int INDEX_GAME_STATE=0;
	private static final int INDEX_PLAYER_NAME=1;
	private static final int INDEX_SCORE=2;
	private static final int INDEX_ATTEMPTS=3;
	private static final int INDEX_HINTWORD=4;
	
	private String playerName;
	private String currentGameState;
	private int score;
	private int attempts;
	private String hintWord;
	
	public GameStatus(String parseMessage){
		String[] fields=parseMessage.split(";");
		this.playerName=fields[INDEX_PLAYER_NAME];
		this.currentGameState=fields[INDEX_GAME_STATE];
		this.score=Integer.parseInt(fields[INDEX_SCORE]);
		this.attempts=Integer.parseInt(fields[INDEX_ATTEMPTS]);
		this.hintWord=fields[INDEX_HINTWORD];
		
	}
	
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getCurrentGameState() {
		return currentGameState;
	}
	public void setCurrentGameState(String currentGameState) {
		this.currentGameState = currentGameState;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public String getHintWord() {
		return hintWord;
	}
	public void setHintWord(String hintWord) {
		this.hintWord = hintWord;
	}
	@Override
	public String toString() {
		return "GameState [playerName=" + playerName + ", currentGameState=" + currentGameState + ", score=" + score
				+ ", attempts=" + attempts + ", hintWord=" + hintWord + "]";
	}
	
	
}
