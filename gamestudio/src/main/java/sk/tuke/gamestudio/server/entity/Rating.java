package sk.tuke.gamestudio.server.entity;

import java.sql.Date;

public class Rating {

	private String player;
	private String game;
	private int rating;
	private Date ratedon;

	public Rating(String player, String game, int rating, Date ratedon) {
		super();
		this.player = player;
		this.game = game;
		this.rating = rating;
		this.ratedon = ratedon;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getRatedon() {
		return ratedon;
	}

	public void setRatedon(Date ratedon) {
		this.ratedon = ratedon;
	}

	@Override
	public String toString() {
		return "(" + player + ", " + game + ", " + rating + ", " + ratedon + ")";
	}

}
