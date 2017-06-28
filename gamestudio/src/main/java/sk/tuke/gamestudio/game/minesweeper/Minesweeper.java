package sk.tuke.gamestudio.game.minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.minesweeper.core.Field;
import sk.tuke.gamestudio.game.minesweeper.core.Mine;

/**
 * Main application class.
 */

public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;

	private long startMillis;

	//private BestTimes bestTimes;

	private static Minesweeper instance;

	private Settings settings;
	
	//private Connection connection;

	/**
	 * Constructor.
	 */
	private Minesweeper() {
//		try {
//			connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER, DatabaseSetting.PASSWORD);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		instance = this;
	//	bestTimes = new BestTimes();
	//	Field field = new Field(9,9,10);
	//	userInterface = new ConsoleUI(field);
		//userInterface.chooseDifficulty();
		//settings = Settings.load();	
		//Field field = new Field(settings.getRowCount(),settings.getColumnCount(),settings.getMineCount());
		startMillis = System.currentTimeMillis();
	//	userInterface.newGameStarted();
	
				
	}
	


	public static Minesweeper getInstance() {
		if (instance == null) {
			return new Minesweeper();
		}
		return instance;
	}

	public int getPlayingSeconds() {
		return (int) ((System.currentTimeMillis() - startMillis)/1000);
	}

//	public BestTimes getBestTimes() {
//		return bestTimes;
//	}

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
	//public static void main(String[] args) {
		// new Minesweeper();
	//	getInstance();
		//getInstance().play();


	//}

}
