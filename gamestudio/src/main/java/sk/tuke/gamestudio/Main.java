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
import sk.tuke.gamestudio.server.service.JPA.CommentServiceJPA;
import sk.tuke.gamestudio.server.service.JPA.GameplayServiceJPA;
import sk.tuke.gamestudio.server.service.JPA.RatingServiceJPA;
import sk.tuke.gamestudio.server.service.JPA.ScoreServiceJPA;
import sk.tuke.gamestudio.server.service.SORM.CommentServiceSORM;
import sk.tuke.gamestudio.server.service.SORM.RatingServiceSORM;
import sk.tuke.gamestudio.server.service.SORM.ScoreServiceSORM;

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

//	@Bean
//	public ScoreServiceJDBC scoreService() {
//		return new ScoreServiceJDBC();
//	}
//
//	@Bean
//	public CommentServiceJDBC commentService() {
//		return new CommentServiceJDBC();
//	}
//
//	@Bean
//	public RatingServiceJDBC ratingService() {
//		return new RatingServiceJDBC();
//	}

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
	
//	@Bean
//	public CommentServiceSORM commentServiceSORM(){
//		return new CommentServiceSORM();
//	}
//	
//	@Bean
//	public RatingServiceSORM ratingServiceSORM(){
//		return new RatingServiceSORM();
//	}
//	
//	@Bean
//	public ScoreServiceSORM scoreServiceSORM(){
//		return new ScoreServiceSORM();
//	}

	@Bean
	public ScoreServiceJPA scoreServiceJPA(){
		return new ScoreServiceJPA();
	}
	@Bean 
	public RatingServiceJPA ratingServiceJPA(){
		return new RatingServiceJPA();
	}
	@Bean
	public CommentServiceJPA commentServiceJPA(){
		return new CommentServiceJPA();
	}
	
	@Bean
	public GameplayServiceJPA gameplayServiceJPA(){
		return new GameplayServiceJPA();
	}
}