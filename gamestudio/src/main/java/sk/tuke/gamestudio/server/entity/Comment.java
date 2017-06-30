package sk.tuke.gamestudio.server.entity;

import java.sql.Date;

import sk.tuke.gamestudio.server.service.annotation.*;

@Table(name = "comment", createDrop = false)
public class Comment {
	
	@Column(name = "player")
	private String player;
	@Column(name = "game")
	private String game;
	@Column(name = "comment")
	private String comment;
	@Column(name = "commentedon")
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
