package sk.tuke.gamestudio;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.tuke.gamestudio.game.GameUserInterface;
import sk.tuke.gamestudio.game.Games;
import sk.tuke.gamestudio.game.GamestudioUI;
import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUIKamene;
import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUIMinesweeper;
import sk.tuke.gamestudio.server.service.JDBC.CommentServiceJDBC;
import sk.tuke.gamestudio.server.service.JDBC.RatingServiceJDBC;
import sk.tuke.gamestudio.server.service.JDBC.ScoreServiceJDBC;

@Configuration
@SpringBootApplication
public class Main {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner runner(GamestudioUI ui) {
		return args -> ui.start();
	}

	@Bean
	public GamestudioUI gamestudioUI() {
		return new GamestudioUI();
	}

	@Bean
	public ScoreServiceJDBC scoreService() {
		return new ScoreServiceJDBC();
	}

	@Bean
	public CommentServiceJDBC commentService() {
		return new CommentServiceJDBC();
	}

	@Bean
	public RatingServiceJDBC ratingService() {
		return new RatingServiceJDBC();
	}

	@Bean
	public ConsoleUIKamene consoleUIKamene() {
		return new ConsoleUIKamene();
	}

	@Bean
	public ConsoleUIMinesweeper consoleUIMines() {
		return new ConsoleUIMinesweeper();
	}

	@Bean
	public Map<Games, GameUserInterface> games() {
		Map<Games, GameUserInterface> games = new HashMap<>();
		games.put(Games.KAMENE, consoleUIKamene());
		games.put(Games.MINESWEEPER, consoleUIMines());
		return games;
	}

}