package sk.tuke.gamestudio.game.pexeso.core;

public class Tile {

	public enum State {
		OPEN,
		CLOSED
	}
	
	private State state = State.CLOSED;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
