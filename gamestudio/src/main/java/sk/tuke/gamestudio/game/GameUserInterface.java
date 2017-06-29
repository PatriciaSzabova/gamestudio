package sk.tuke.gamestudio.game;

import sk.tuke.gamestudio.game.kamene.core.FieldKamene;
import sk.tuke.gamestudio.server.entity.Score;

public interface GameUserInterface {

	/**
	 * Starts the game.
	 * 
	 * @param field
	 *            field of mines and clues
	 */
	Score newGameStarted();

	/**
	 * Updates user interface - prints the field.
	 */
	void update();

	/**
	 * Selects the size of the field.
	 * 
	 * @throws WrongFormatException
	 */
	void choseFieldSize() throws WrongFormatException;
	
	void loadLastField() throws WrongFormatException;

}
