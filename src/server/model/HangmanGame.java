package server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

import common.Constants;

public class HangmanGame {
	private Player player;
	private HintWord hintWord;
	private Word word;
	private int attempts;
	private String stateMessage;
	
	public HangmanGame(Player player) {
		this.player=player;
	}
	
	
	public void updateStateMessage() {
		StringJoiner sj=new StringJoiner(Constants.MSG_BODY_DELIMETER);
		sj.add(this.getPlayer().getName());
		sj.add(new String(""+this.getPlayer().getScore()));
		sj.add(new String(""+this.getAttempts()));
		sj.add(new String(this.getHintWord().toStringWord()));
		this.stateMessage=sj.toString();
	}
	public String getStateMessage() {
		return stateMessage;
	}
	public void setStateMessage(String stateMessage) {
		this.stateMessage = stateMessage;
	}
	public void initWord() {
		String randomWord=this.readRandomWord();
		this.word=new Word(randomWord);		
		int wordLength=word.getNumLetters();
		this.hintWord=new HintWord(wordLength);
		this.attempts=wordLength;
		updateStateMessage();
	}
	
	private String readRandomWord() {
		String wordFile="words.txt";
		ArrayList<String> wordList=new ArrayList<String>();
		
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(wordFile))) {
                String s;
                while ((s = br.readLine()) != null) {
                    wordList.add(s);
                }
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, wordList.size());
            return wordList.get(randomNum);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }	
	}
	
	
	
	private boolean checkLetter(char input) {
		boolean flag=false;
		char[] wordLetters=word.getWord().toCharArray();
		for(int i=0;i<word.getNumLetters();i++) {
			if(input==wordLetters[i]) {
				hintWord.getLetters()[i]=input;
				flag=true;
			}
		}
		return flag;
	}
	
	private boolean checkWholeWord(String input) {
		return input.equals(word.getWord());
	}
	
	public int oneRound(String input) {
		int flag;
		
		if(input.length()>1) {
			if(checkWholeWord(input)) {
				hintWord.setLetters(input);
				win();
				return 1;
			}else {
				attempts--;
				if(attempts==0) {
					lose();
					return -1;
				}
				updateStateMessage();
				return 0;
			}
		}else {
			char inputChar=input.toCharArray()[0];
			if(checkLetter(inputChar)) {
				for(char c:hintWord.getLetters()) {
					if(c=='_') {
						updateStateMessage();
						return 0;
					}
				}
				win();
				return 1;
			}else {
				attempts--;
				if(attempts==0) {
					lose();
					return -1;
				}
				updateStateMessage();
				return 0;
			}
		}
	}
	
	
	private void lose() {
		//System.out.println("You lose");
		player.minusScore();
		updateStateMessage();
		initWord();
	}
	private void win(){
		//System.out.println("You win");
		player.addScore();
		updateStateMessage();
		initWord();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public HintWord getHintWord() {
		return hintWord;
	}

	public void setHintWord(HintWord hintWord) {
		this.hintWord = hintWord;
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
	
	
	
}
