package sk.tuke.gamestudio.game.minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sk.tuke.gamestudio.game.GameUserInterface;
import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.minesweeper.core.Field;
import sk.tuke.gamestudio.game.minesweeper.core.Mine;

/**
 * Main application class.
 */

public class Minesweeper {
	/** User interface. */
	private GameUserInterface userInterface;

	private long startMillis;

	private static Minesweeper instance;

	private Settings settings;

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance = this;
		// Field field = new Field(9,9,10);
		// userInterface = new ConsoleUI(field);
		// GameUserInterface.chooseDifficulty();
		// settings = Settings.load();
		// Field field = new
		// Field(settings.getRowCount(),settings.getColumnCount(),settings.getMineCount());
		startMillis = System.currentTimeMillis();
		// GameUserInterface.newGameStarted();

	}

	public static Minesweeper getInstance() {
		if (instance == null) {
			return new Minesweeper();
		}
		return instance;
	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		settings.save();
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	// public static void main(String[] args) {
	// new Minesweeper();
	// getInstance();
	// getInstance().play();

	// }

}
