package sk.tuke.gamestudio.server.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sk.tuke.gamestudio.server.entity.Rating;
import sk.tuke.gamestudio.server.entity.Score;

public class RatingServiceJDBC implements RatingService {

	private Rating rating;
	private int avgRating;

	private static final String INSERT_QUERY = "INSERT INTO rating (player, game, rating, ratedon) VALUES (?,?,?,?)";
	private static final String SELECT_RATING_QUERY = "SELECT player, game, rating, ratedon FROM rating "
			+ "WHERE player = ? AND game = ?";
	private static final String SELECT_AVG_RATING_QUERY = "SELECT AVG(rating) FROM rating WHERE game = ?";
	private static final String UPDATE_RATING = "UPDATE rating SET rating = ? WHERE player = ? AND game = ?";

	@Override
	public void setRating(Rating rating) throws RatingException {
		if (selectFromDB(rating.getGame(), rating.getPlayer())) {
			updateRating(rating.getRating(), rating.getPlayer());
		} else {
			insertToDb(rating);
		}

	}

	@Override
	public int getAverageRating(String game) throws RatingException {
		selectAvgRatingDB(game);
		return avgRating;
	}

	@Override
	public int getRating(String game, String player) throws RatingException {
		selectFromDB(game, player);
		return rating.getRating();
	}

	private void insertToDb(Rating rating) {
		try (Connection connection = DriverManager.getConnection(DatabaseSettings.URL, DatabaseSettings.USER,
				DatabaseSettings.PASSWORD); PreparedStatement pstm = connection.prepareStatement(INSERT_QUERY)) {
			pstm.setString(1, rating.getPlayer());
			pstm.setString(2, rating.getGame());
			pstm.setInt(3, rating.getRating());
			pstm.setDate(4, rating.getRatedon());
			pstm.execute();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private boolean selectFromDB(String game, String player){
		try (Connection connection = DriverManager.getConnection(DatabaseSettings.URL, DatabaseSettings.USER,
				DatabaseSettings.PASSWORD); PreparedStatement pstm = connection.prepareStatement(SELECT_RATING_QUERY)) {
			pstm.setString(1, player);
			pstm.setString(2, game);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Rating rt = new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDate(4));
				rating = rt;
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void selectAvgRatingDB(String game) {
		try (Connection connection = DriverManager.getConnection(DatabaseSettings.URL, DatabaseSettings.USER,
				DatabaseSettings.PASSWORD);
				PreparedStatement pstm = connection.prepareStatement(SELECT_AVG_RATING_QUERY)) {
			pstm.setString(1, game);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				avgRating = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateRating(int rating, String player) {
		try (Connection connection = DriverManager.getConnection(DatabaseSettings.URL, DatabaseSettings.USER,
				DatabaseSettings.PASSWORD); PreparedStatement pstm = connection.prepareStatement(UPDATE_RATING)) {
			pstm.setInt(1, rating);
			pstm.setString(2, player);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
