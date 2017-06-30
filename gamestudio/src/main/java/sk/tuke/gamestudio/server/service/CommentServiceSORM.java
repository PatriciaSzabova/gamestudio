package sk.tuke.gamestudio.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import sk.tuke.gamestudio.server.entity.Comment;

public class CommentServiceSORM implements CommentService {

	@Override
	public void addComment(Comment comment) throws CommentException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Comment> getComments(String game) throws CommentException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInsertString(Class<?> clazz) {
		return String.format("INSERT INTO %s (%s) VALUES (%s)", clazz.getSimpleName(),
				Arrays.stream(clazz.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.joining(", ")),
				Arrays.stream(clazz.getDeclaredFields()).map(f -> "?").collect(Collectors.joining(", ")));
	}

}
