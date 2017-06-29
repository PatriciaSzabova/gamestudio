package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.tuke.gamestudio.game.GamestudioUI;

//import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUI;
//import sk.tuke.gamestudio.game.minesweeper.core.Field;

import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUIKamene;
import sk.tuke.gamestudio.game.kamene.core.FieldKamene;
import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUIMinesweeper;
import sk.tuke.gamestudio.game.minesweeper.core.FieldMines;
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
	public ConsoleUIKamene consoleUIKamene(FieldKamene field) {
		return new ConsoleUIKamene(field);
	}

	@Bean
	public FieldKamene fieldStones() {
		return new FieldKamene(4, 4);
	}

	@Bean
	public ConsoleUIMinesweeper consoleUIMines(FieldMines field) {
		return new ConsoleUIMinesweeper(field);
	}

	@Bean
	public FieldMines fieldMines() {
		return new FieldMines(9, 9, 20);
	}
}