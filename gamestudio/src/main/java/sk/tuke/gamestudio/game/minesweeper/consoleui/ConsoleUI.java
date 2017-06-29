package sk.tuke.gamestudio.game.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import sk.tuke.gamestudio.game.kamene.Kamene;
import sk.tuke.gamestudio.game.kamene.consoleui.WrongFormatException;
import sk.tuke.gamestudio.game.minesweeper.Minesweeper;
import sk.tuke.gamestudio.game.minesweeper.Settings;
import sk.tuke.gamestudio.game.GameUserInterface;
import sk.tuke.gamestudio.game.minesweeper.core.Field;
import sk.tuke.gamestudio.game.minesweeper.core.GameState;
import sk.tuke.gamestudio.game.minesweeper.core.Tile;
import sk.tuke.gamestudio.server.entity.Comment;
import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;
import sk.tuke.gamestudio.server.service.CommentException;
import sk.tuke.gamestudio.server.service.CommentServiceJDBC;
import sk.tuke.gamestudio.server.service.RatingException;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;
import sk.tuke.gamestudio.server.service.ScoreException;
import sk.tuke.gamestudio.server.service.ScoreServiceJDBC;

/**
 * Console user interface.
 */
public class ConsoleUI implements GameUserInterface {
	/** Playing field. */
	private Field field;

	// Date date = new Date();
	// DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private int randomRow;
	private int randomCol;

	@Autowired
	ScoreServiceJDBC scoreService;
	@Autowired
	CommentServiceJDBC commentService;
	@Autowired
	RatingServiceJDBC ratingService;

	private RandomOpenThread thread = new RandomOpenThread();

	public ConsoleUI(Field field) {
		this.field = field;
	}

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.
	 * Field)
	 */
	@Override
	public void newGameStarted() {
		StringBuilder sb = new StringBuilder();
		sb.append(df.format(getSQLCurrentDate())).append("\n\n").append("Let's play a game,")
				.append(System.getProperty("user.name")).append("\n");
		System.out.println(sb.toString());
		synchronized (this) {
			thread.start();
		}

		this.field = field;
		do {
			update();
			processInput();

		} while (field.getState() == GameState.PLAYING);

		if (field.getState() == GameState.SOLVED) {
			System.out.println("Congratulations! You have WON! :D ");
			update();
			Score score = new Score(System.getProperty("user.name"), "mines",
					1000 - (Minesweeper.getInstance().getPlayingSeconds() / 100), getSQLCurrentDate());
			try {
				scoreService.addScore(score);
			} catch (ScoreException e) {
				e.getMessage();
			}
			try {
				System.out.println(scoreService.getBestScores("mines").toString());
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
				System.out.println(commentService.getComments("mines").toString());
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
				System.out.println(ratingService.getAverageRating("mines"));
			} catch (RatingException e) {
				e.printStackTrace();
			}
			System.out.println("Your rating:");
			try {
				System.out.println(ratingService.getRating("mines", System.getProperty("user.name")));
			} catch (RatingException e) {
				e.printStackTrace();
			}

		} else if (field.getState() == GameState.FAILED) {
			System.out.println("You have FAILED! :(");
			update();

		}
		System.exit(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		Formatter f = new Formatter();
		f.format("Playing time: %s%n%nRemaining mines: %s%n", Minesweeper.getInstance().getPlayingSeconds(),
				field.getRemainingMineCount());
		System.out.println(f.toString());
		System.out.println(field);
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		String s = "Please enter your selection <X> EXIT GAME, <MA1> Mark tile (A1), <OB4> Open tile (B4): ";
		System.out.println(s);
		String input = readLine().toUpperCase();
		try {
			handleInput(input);
		} catch (WrongFormatException e) {
			System.out.println(e.getMessage());
		}

	}

	private void handleInput(String input) throws WrongFormatException {
		Pattern pattern = Pattern.compile("(O|M)([A-Z])([0-9]+)");
		Matcher matcher = pattern.matcher(input);

		if (input.equals("X")) {
			System.out.println("You have exited the game");
			System.exit(0);
		} else if (matcher.matches()) {
			int row = matcher.group(2).charAt(0) - 'A';
			int column = Integer.parseInt(matcher.group(3));
			if (row > field.getRowCount() || column > field.getColumnCount()) {
				throw new WrongFormatException("Field is not large enough. Go smaller, buddy");
			}
			if (matcher.group(1).charAt(0) == 'O') {
				field.openTile(row, column);
			} else {
				field.markTile(row, column);
			}
			thread.actionWasPerformed();
			thread = new RandomOpenThread();
			thread.start();
		} else {
			throw new WrongFormatException("Wrong Input. Enter again!");

		}

	}

	@Override
	public void choseFieldSize() throws WrongFormatException {
		StringBuilder sb = new StringBuilder();
		sb.append("Chose your game difficulty: \n").append("1. BEGINNER \n").append("2. INTERMEDIATE \n")
				.append("3. EXPERT \n").append("4. CUSTOM \n").append("5. LOAD LAST PLAYED SETTNG");
		System.out.println(sb.toString());
		String choice = readLine();
		int selectionNumber = Integer.parseInt(choice);
		switch (selectionNumber) {
		case 1:
			Minesweeper.getInstance().setSettings(Settings.BEGINNER);
			break;
		case 2:
			Minesweeper.getInstance().setSettings(Settings.INTERMEDIATE);
			break;
		case 3:
			Minesweeper.getInstance().setSettings(Settings.EXPERT);
			break;
		case 4:
			System.out.println("Enter number of rows:");
			String rowNumber = readLine();
			int rowCount = Integer.parseInt(rowNumber);
			System.out.println("Enter number of columns:");
			String colNumber = readLine();
			int columnCount = Integer.parseInt(colNumber);
			System.out.println("Enter number of mines:");
			String mineNumber = readLine();
			int mineCount = Integer.parseInt(mineNumber);
			Minesweeper.getInstance().setSettings(new Settings(rowCount, columnCount, mineCount));
			break;
		case 5:
			try {
				loadLastField();
			} catch (WrongFormatException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Wrong input");
			break;
		}

	}

	private void commentOption() throws WrongFormatException {
		System.out.println("Would you like to leave a comment? Y/N");
		String choice = readLine();
		if (choice.toUpperCase().equals("Y")) {
			System.out.println("Enter your comment:");
			String userComment = readLine();
			Comment cmt = new Comment(System.getProperty("user.name"), "mines", userComment, getSQLCurrentDate());
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
				Rating rt = new Rating(System.getProperty("user.name"), "mines", Integer.parseInt(userRating),
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

	private void openRandomTile(Field field) {
		boolean isValid = false;
		randomRow = 0;
		randomCol = 0;
		Random r = new Random();
		do {
			randomRow = r.nextInt(field.getRowCount());
			randomCol = r.nextInt(field.getColumnCount());
			if (field.getTile(randomRow, randomCol).getState().equals(Tile.State.CLOSED)) {
				field.openTile(randomRow, randomCol);
				isValid = true;
			}

		} while (isValid = false);
	}

	@Override
	public void loadLastField() throws WrongFormatException {
		Minesweeper.getInstance().setSettings(Settings.load());

	}

	private java.sql.Date getSQLCurrentDate() {
		return new java.sql.Date(new Date().getTime());
	}

	private class RandomOpenThread extends Thread {
		private boolean actionPerformed = false;

		@Override
		public void run() {
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!actionPerformed) {
				openRandomTile(field);
				update();
				thread.actionWasPerformed();
				thread = new RandomOpenThread();
				thread.start();
			}
		}

		public void actionWasPerformed() {
			this.actionPerformed = true;
		}

	}

}
