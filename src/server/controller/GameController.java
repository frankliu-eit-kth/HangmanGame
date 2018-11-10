package server.controller;

import java.util.StringJoiner;

import common.Constants;
import server.model.HangmanGame;
import server.model.Player;

public class GameController {
	private HangmanGame game;
	
	public GameController() {
		super();
	}
	
	public void newGame(String playerIp) {
		try {
		Player player=new Player(playerIp,0,"player");
		game=new HangmanGame(player);
		}catch(Exception e) {
			System.out.println("game controller create new game failed");
			e.printStackTrace();
		}
	}
	
	private String stateMessage(String command) {
		StringJoiner sj=new StringJoiner(Constants.MSG_BODY_DELIMETER);
		String stateMessage=this.game.getStateMessage();
		
		if(command.equals("start")||command.equals("continue")||command.equals("win")||command.equals("lose")) {
			sj.add(command);
			sj.add(stateMessage);
			return sj.toString();
		}else {
			System.out.println("unknown command when generating state message");
			return null;
		}
	}
	
	public String start() {
		this.game.initWord();
		return stateMessage("start");
	}
	
	public String changeUserName(String newName) {
		try {
			this.game.getPlayer().setName(newName);
			System.out.println("chang user name to"+newName);
			return stateMessage("continue");
			
		}catch(Exception e) {
			System.out.println("create new name failed");
			e.printStackTrace();
			return null;
		}
	}
	
	public String executeRound(String input) {
		int flag=this.game.oneRound(input);
		
		if(flag==1) {
			return stateMessage("win");
		}
		else {
			if(flag==0) {
				return stateMessage("continue");
			}
			else {
				if(flag==-1) {
					return stateMessage("lose");
				}
				else {
					System.out.println("execute round innormal result");
					return null;
				}
			}
		}
	}
	
	
}
