package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.GamestudioUI;
import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUIKamene;
import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUIMinesweeper;
import sk.tuke.gamestudio.server.service.CommentServiceJDBC;
import sk.tuke.gamestudio.server.service.RatingServiceJDBC;
import sk.tuke.gamestudio.server.service.ScoreServiceJDBC;

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

}