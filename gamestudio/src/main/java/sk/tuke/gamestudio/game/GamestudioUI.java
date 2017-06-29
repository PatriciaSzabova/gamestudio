package sk.tuke.gamestudio.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;

import sk.tuke.gamestudio.Main;
import sk.tuke.gamestudio.game.kamene.Kamene;
import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUIKamene;
import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUIMinesweeper;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.CommentException;
import sk.tuke.gamestudio.server.service.CommentServiceJDBC;
import sk.tuke.gamestudio.server.service.RatingException;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;
import sk.tuke.gamestudio.server.service.ScoreException;
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
	private ConsoleUIKamene consoleUIStones;
	@Autowired
	private ConsoleUIMinesweeper consoleUIMines;

	private String currentGamePlayed;

	private Score gameScore;

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
			pickedGameIdx = processInput() - 1;
		} catch (WrongFormatException e) {
			e.getMessage();
		}
		String pickedGame = allGames[pickedGameIdx].toString();
		switch (pickedGame) {
		case "Minesweeper":
			currentGamePlayed = "mines";
			gameScore = consoleUIMines.newGameStarted();
			break;
		case "Kamene":
			currentGamePlayed = "kamene";
			gameScore = consoleUIStones.newGameStarted();
			break;
		default:
			System.out.println("No such game exists");
		}
		if (gameScore != null) {
			System.out.println("Your score for this game is: " + gameScore);
			try {
				scoreService.addScore(gameScore);
			} catch (ScoreException e) {
				e.getMessage();
			}
		}

		try {
			System.out.println(scoreService.getBestScores(currentGamePlayed).toString());
		} catch (ScoreException e) {
			e.getMessage();
		}
		try {
			commentOption();
		} catch (WrongFormatException e) {
			e.getMessage();
		}
		System.out.println("Other comments: ");
		try {
			System.out.println(commentService.getComments(currentGamePlayed).toString());
		} catch (CommentException e1) {
			e1.getMessage();
		}
		try {
			ratingOption();
		} catch (WrongFormatException e) {
			e.getMessage();
		}
		System.out.println("Average rating:");
		try {
			System.out.println(ratingService.getAverageRating(currentGamePlayed));
		} catch (RatingException e) {
			e.printStackTrace();
		}
		currentGamePlayed = "";
		System.exit(0);

	}

	private int processInput() throws WrongFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("Pick your game:\n");
		for (int i = 0; i < allGames.length; i++) {
			sb.append(i + 1).append(". ").append(allGames[i]).append("\n");
		}
		sb.append("or press <X> to exit Gamestudio");
		System.out.println(sb.toString());
		String input = readLine().toUpperCase();
		return handleInput(input);

	}

	private int handleInput(String input) throws WrongFormatException {
		int numberOfGames = allGames.length;
		if (input.equals("X")) {
			System.out.println("You have exited Gamestudio");
			System.exit(0);
		} else if (Integer.parseInt(input) <= numberOfGames) {
			return Integer.parseInt(input);
		} else {
			throw new WrongFormatException("Wrong Input!");
		}
		return 0;
	}

	private void commentOption() throws WrongFormatException {
		System.out.println("Would you like to leave a comment? Y/N");
		String choice = readLine();
		if (choice.toUpperCase().equals("Y")) {
			System.out.println("Enter your comment:");
			String userComment = readLine();
			Comment cmt = new Comment(System.getProperty("user.name"), "kamene", userComment, getSQLCurrentDate());
			try {
				commentService.addComment(cmt);
			} catch (CommentException e) {
				e.getMessage();
			}
		} else if (choice.toUpperCase().equals("N")) {
			return;
		} else {
			throw new WrongFormatException("Wrong Input!");
		}
	}

	private void ratingOption() throws WrongFormatException {
		System.out.println("Would you like to rate the game? Y/N");
		String choice = readLine();

		if (choice.toUpperCase().equals("Y")) {
			System.out.println("Enter your rating: (1 for lowest - 5 for highest");
			String userRating = readLine();

			Pattern pattern = Pattern.compile("[1-5]");
			Matcher matcher = pattern.matcher(userRating);

			if (matcher.matches()) {
				Rating rt = new Rating(System.getProperty("user.name"), currentGamePlayed, Integer.parseInt(userRating),
						getSQLCurrentDate());
				try {
					ratingService.setRating(rt);
				} catch (RatingException e) {
					e.printStackTrace();
				}
			}

		} else if (choice.toUpperCase().equals("N")) {
			return;
		} else {
			throw new WrongFormatException("Wrong Input!");
		}
	}

	private java.sql.Date getSQLCurrentDate() {
		return new java.sql.Date(new Date().getTime());
	}

}
