package sk.tuke.gamestudio.game.minesweeper.core;

import java.util.Formatter;
import java.util.Random;

import sk.tuke.gamestudio.game.GameState;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		generate();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * Opens tile at specified indices.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		if (state.equals(GameState.FAILED)) {
			return;
		}
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}
			if (((Clue) tile).getValue() == 0) {
				openAdjacentTiles(row, column);
			}

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
		}
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < getRowCount()) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Clue) {
							openTile(actRow, actColumn);
						}
					}
				}
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		} else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}

	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		Random r = new Random();
		int randomRow, randomColumn;
		int counter = 0;

		do {
			randomRow = r.nextInt(rowCount);
			randomColumn = r.nextInt(columnCount);
			if (!(tiles[randomRow][randomColumn] instanceof Mine)) {
				tiles[randomRow][randomColumn] = new Mine();
				counter++;
			}

		} while (counter < mineCount);

		for (int j = 0; j < rowCount; j++) {
			for (int k = 0; k < columnCount; k++) {
				if (!(tiles[j][k] instanceof Mine)) {
					Clue clue = new Clue(countAdjacentMines(j, k));
					tiles[j][k] = clue;
				}

			}
		}

	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		return ((rowCount * columnCount - getNumberOf(Tile.State.OPEN)) == mineCount);

	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < getRowCount()) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	private int getNumberOf(Tile.State state) {
		int count = 0;
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				if (tiles[i][j].getState() == state) {
					count++;
				}
			}
		}
		return count;
	}

	public int getRemainingMineCount() {
		return mineCount - getNumberOf(Tile.State.MARKED);
	}

	@Override
	public String toString() {
		Formatter f = new Formatter();
		f.format("%s", "  ");
		for (int i = 0; i < columnCount; i++) {
			f.format("%3s", i);
		}
		for (int j = 0; j < rowCount; j++) {
			f.format("%n %s", (char) (j + 65));
			for (int k = 0; k < columnCount; k++) {
				f.format("%3s", tiles[j][k]);
			}
		}
		return f.toString();
	}

}
