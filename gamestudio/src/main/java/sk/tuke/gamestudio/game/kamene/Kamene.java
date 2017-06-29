package sk.tuke.gamestudio.game.kamene;

import sk.tuke.gamestudio.game.GameUserInterface;
import sk.tuke.gamestudio.game.WrongFormatException;
import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUIKamene;
import sk.tuke.gamestudio.game.kamene.core.FieldKamene;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;

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
	}

	/**
	 * Starts a new game.
	 */
	public void startNewGame() {
		try {
			userInterface.choseFieldSize();
		} catch (WrongFormatException e) {
			e.getMessage();
		}

		settings = this.getSettings();
		FieldKamene field = new FieldKamene(settings.getRowCount(), settings.getColumnCount());
		startMillis = System.currentTimeMillis();
		userInterface.newGameStarted();
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

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */

}
