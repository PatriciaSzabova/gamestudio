package sk.tuke.gamestudio.game.pexeso.core;

import java.util.Random;

import org.hibernate.sql.SelectValues;

import sk.tuke.gamestudio.game.GameState;

public class Field {

	private final Tile[][] tiles;
	private final int rowCount;
	private final int columnCount;
	private int opened;
	Tile firstOpened;
	Tile secondOpened;
	private int firstOpenedRow;
	private int firstOpenedCol;
	private int secondOpenedRow;
	private int secondOpenedCol;
	boolean isPair;

	private GameState gameState = GameState.PLAYING;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];

		generate();
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public int getOpened() {
		return opened;
	}

	// public void setOpened(int opened) {
	// this.opened = opened;
	// }

	private void generate() {
		int counter = 1;
		int number = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = new Tile();
				tiles[row][column].setValue(number);
				if (counter == 2) {
					number++;
					counter = 0;
				}
				counter++;
			}
		}
		shuffle();
	}

	private void shuffle() {
		Random r = new Random();
		int randomRow = r.nextInt(rowCount);
		int randomCol = r.nextInt(columnCount);
		int neighbourValue = -1;
		Tile temp;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				neighbourValue = getNeighobourValue(row, col + 1);
				if (tiles[row][col].getValue() == neighbourValue) {
					temp = tiles[row][col];
					tiles[row][col] = tiles[randomRow][randomCol];
					tiles[randomRow][randomCol] = temp;
				}
				randomRow = r.nextInt(rowCount);
				randomCol = r.nextInt(columnCount);
			}
		}

	}

	private int getNeighobourValue(int row, int col) {
		if (col >= columnCount) {
			return -1;
		}
		return tiles[row][col].getValue();
	}

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			if (firstOpened == null) {
				tile.setState(Tile.State.OPEN);
				opened++;
				firstOpened = tile;
				firstOpenedRow = row;
				firstOpenedCol = column;
			}
			if (secondOpened == null && firstOpened != tile) {
				tile.setState(Tile.State.OPEN);
				opened++;
				secondOpened = tile;
				secondOpenedRow = row;
				secondOpenedCol = column;
			}
		}
		if (opened == 2) {
			if (checkPairs(firstOpened, secondOpened)) {
				firstOpened.setState(Tile.State.SOLVED);
				secondOpened.setState(Tile.State.SOLVED);
			}
			closeAllTiles();
			opened = 0;
		}

	}

	private boolean checkPairs(Tile t1, Tile t2) {
		if (t1.getValue() == t2.getValue()) {
			return true;
		}
		return false;
	}

	// private void fixTile(int row, int column){
	// Tile tile = tiles[row][column];
	// tile.setState(Tile.State.WAITING);
	// }

	private void closeTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.OPEN) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	private void closeAllTiles() {
		closeTile(firstOpenedRow, firstOpenedCol);
		closeTile(secondOpenedRow, secondOpenedCol);
	}

	private boolean isSolved() {
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				if (tiles[row][col].getState() != Tile.State.SOLVED) {
					return false;
				}
			}
		}
		return true;
	}

}
