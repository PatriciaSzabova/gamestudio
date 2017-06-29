package sk.tuke.gamestudio.game.minesweeper.core;

import sk.tuke.gamestudio.game.minesweeper.core.Tile.State;

/**
 * Tile of a field.
 */
public abstract class Tile {
    
    /** Tile states. */
    public enum State {
        /** Open tile. */
        OPEN, 
        /** Closed tile. */
        CLOSED,
        /** Marked tile. */
        MARKED
    }
    
    /** Tile state. */
    private State state = State.CLOSED;
        
    /**
     * Returns current state of this tile.
     * @return current state of this tile
     */
    public State getState() {
        return state;
    }

    /**
     * Sets current current state of this tile.
     * @param state current state of this tile
     */
    void setState(State state) {
        this.state = state;
    }

	@Override
	public String toString() {
		return this.getState() == State.CLOSED ? "-" : "M";
	}
}