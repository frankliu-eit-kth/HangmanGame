package Test;

import server.controller.GameController;
import server.model.HangmanGame;
import server.model.Player;

public class TestGameController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameController controller=new GameController();
		controller.newGame("1.1.1.1");
		controller.changeUserName("frank");
		System.out.println(controller.executeRound("asdf"));
		System.out.println(controller.executeRound("b"));
		System.out.println(controller.executeRound("c"));
		System.out.println(controller.executeRound("d"));
		System.out.println(controller.executeRound("e"));
		
		
	}

}
