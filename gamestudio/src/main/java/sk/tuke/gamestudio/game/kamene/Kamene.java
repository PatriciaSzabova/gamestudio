package sk.tuke.gamestudio.game.kamene;

import sk.tuke.gamestudio.game.GameUserInterface;
import sk.tuke.gamestudio.game.WrongFormatException;

/**
 * Main application class
 * 
 * @author P3502714
 *
 */
public class Kamene {
	/** User interface */
	private GameUserInterface userInterface;

	private static Kamene instance;

	/** Starting time of the game */
	private long startMillis;

	/** settings */
	private Settings settings;

	/**
	 * Constructor
	 */
	public Kamene() {
		instance = this;
		// GameUserInterface = new ConsoleUI();
		// try {
		// userInterface.loadLastField();
		// } catch (WrongFormatException e) {
		// e.getMessage();
		// }
		startMillis = System.currentTimeMillis();
	}

	/**
	 * Starts a new game.
	 */
	public void startNewGame() {
		try {
			userInterface.chooseFieldSize();
		} catch (WrongFormatException e) {
			e.getMessage();
		}

		settings = this.getSettings();
		// Field field = new Field(settings.getRowCount(),
		// settings.getColumnCount());
		// userInterface.newGameStarted();
	}

	public static Kamene getInstance() {
		if (instance == null) {
			return new Kamene();
		}
		return instance;

	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis) / 1000);
	}

	public void setStartMillis(long startMillis) {
		this.startMillis = startMillis;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

}
