package sk.tuke.gamestudio.server.entity;

import java.sql.Date;

public class Comment {
	
	private String player;
	private String game;
	private String comment;
	private Date commentedon;

	public Comment(String player, String game, String comment, Date commentedon) {
		super();
		this.player = player;
		this.game = game;
		this.comment = comment;
		this.commentedon = commentedon;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentedon() {
		return commentedon;
	}

	public void setCommentedon(Date commentedon) {
		this.commentedon = commentedon;
	}

	@Override
	public String toString() {
		return "(" + player + ", " + ", " + comment + ", " + commentedon
				+ ")";
	}

}
