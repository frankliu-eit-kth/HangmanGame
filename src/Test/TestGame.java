package Test;

import server.model.HangmanGame;
import server.model.HintWord;
import server.model.Player;
import server.model.Word;

public class TestGame {
	public static void main(String args[]) {
		Player player=new Player("1.1.1.1",1,"frank");
		
		HangmanGame game=new HangmanGame(player);
	
		
		
		Word word=new Word("hangman");
		HintWord hintword=new HintWord(7);
		game.setWord(word);;
		game.setHintWord(hintword);
		game.setAttempts(word.getWord().length());
		
		
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		//game.initWord();
		game.oneRound("h");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("a");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("n");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("g");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("m");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		

		game.oneRound("a");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("b");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("c");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("d");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("e");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("f");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("g");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("h");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("i");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("j");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("k");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("l");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		game.oneRound("m");
		System.out.println(new String(new String(game.getHintWord().getLetters())));
		System.out.println(game.getPlayer().toString());
		System.out.println(game.getAttempts());
		
		

		
		
		
	}
}
