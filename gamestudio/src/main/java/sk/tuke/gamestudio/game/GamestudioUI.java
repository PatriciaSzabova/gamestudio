package sk.tuke.gamestudio.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;

import sk.tuke.gamestudio.Main;
import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUIKamene;
import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUIMinesweeper;
import sk.tuke.gamestudio.server.service.CommentServiceJDBC;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;
import sk.tuke.gamestudio.server.service.ScoreServiceJDBC;

public class GamestudioUI {

	private Games[] allGames;
	@Autowired
	private ScoreServiceJDBC scoreService;
	@Autowired
	private CommentServiceJDBC commentService;
	@Autowired
	private RatingServiceJDBC ratingService;
	@Autowired
	//@Qualifier("fieldStones")
	private ConsoleUIKamene consoleUIStones;
	@Autowired
	private ConsoleUIMinesweeper consoleUIMines;
	
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public GamestudioUI() {
		allGames = Games.values();
	}

	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	public void start() {
		int pickedGameIdx = -1;
		
		try {
			pickedGameIdx = processInput()-1;
		} catch (WrongFormatException e) {
			e.getMessage();
		}
		String pickedGame = allGames[pickedGameIdx].toString();
		switch(pickedGame){
		case "Minesweeper": 
			consoleUIMines.newGameStarted();
			break;
		case "Kamene":
			consoleUIStones.newGameStarted();
			break;
		default:
			System.out.println("No such game exists");
		}
		
			
		

	}

	private int processInput() throws WrongFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("Pick your game:\n");
		for (int i = 0; i < allGames.length; i++) {
			sb.append(i + 1).append(allGames[i]).append("\n");
		}
		sb.append("or press <X> to exit Gamestudio");
		System.out.println(sb.toString());
		String input = readLine().toUpperCase();
		return handleInput(input);

	}

	private int handleInput(String input) throws WrongFormatException {
		int numberOfGames = allGames.length;		
		if(input.equals("X")){
			System.out.println("You have exited Gamestudio");
			System.exit(0);
		}else if(Integer.parseInt(input)<= numberOfGames){
			return Integer.parseInt(input);
		}else{
			throw new WrongFormatException("Wrong Input!");
		}
		return 0;
	}

}
