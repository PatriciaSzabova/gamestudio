package sk.tuke.gamestudio.game.minesweeper;

import sk.tuke.gamestudio.game.minesweeper.core.Field;

public interface UserInterface {

	/**
	 * Starts the game.
	 * @param field field of mines and clues
	 */
	void newGameStarted();

	/**
	 * Updates user interface - prints the field.
	 */
	void update();
	
	void chooseDifficulty();

}