package sk.tuke.gamestudio.server.entity;

import java.sql.Date;

public class Score {
	
	private String player;
	private String game;
	private int points;
	private Date playedon;
	
	public Score(String player, String game, int points, Date playedon) {
		super();
		this.player = player;
		this.game = game;
		this.points = points;
		this.playedon = playedon;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Date getPlayedon() {
		return playedon;
	}

	public void setPlayedon(Date playedon) {
		this.playedon = playedon;
	}
	
	@Override
	public String toString() {
		return "("+player + ", " + game + ", " + points + ", " + playedon+")";
	}
	
	
	
	

}
