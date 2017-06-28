package sk.tuke.gamestudio.game.minesweeper.core;

import sk.tuke.gamestudio.game.minesweeper.core.Tile.State;

/**
 * Mine tile.
 */
public class Mine extends Tile {

	@Override
	public String toString() {
		return getState() == Tile.State.OPEN ? "X" : super.toString();
	}

}
