package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import sk.tuke.gamestudio.game.minesweeper.consoleui.ConsoleUI;
//import sk.tuke.gamestudio.game.minesweeper.core.Field;

import sk.tuke.gamestudio.game.kamene.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.kamene.core.Field;
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
    public CommandLineRunner runner(ConsoleUI ui) { return args -> ui.newGameStarted();}

    @Bean
    public ConsoleUI consoleUI(Field field) { return new ConsoleUI(field); }

    @Bean
    public Field field() { return new Field(9, 9); }
    
    @Bean
    public ScoreServiceJDBC scoreService(){
    	return new ScoreServiceJDBC();
    }
    
    @Bean
    public CommentServiceJDBC commentService(){
    	return new CommentServiceJDBC();
    }
    
    @Bean
    public RatingServiceJDBC ratingService(){
    	return new RatingServiceJDBC();
    }
}