package sk.tuke.gamestudio.server.service;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseSettings {

//	protected static final String DATABASE_PROPERTIES = "src/main/resources/application.properties";
//
//	public static String DRIVER_CLASS;
//
//	public static String URL;
//
//	public static String USER;
//
//	public static String PASSWORD;
//
//	private void loadProperties() {
//		try {
//			Properties properties = new Properties();
//			properties.load(getClass().getResourceAsStream(DATABASE_PROPERTIES));
//			URL = properties.getProperty("spring.datasource.url");						
//			USER = properties.getProperty("spring.datasource.username");
//			PASSWORD = properties.getProperty("spring.datasource.password");
//			DRIVER_CLASS = properties.getProperty("spring.datasource.driver-class-name");
//		} catch (IOException e) {
//			e.getStackTrace();
//		}
//	}
	
	public static String DRIVER_CLASS = "org.postgresql.Driver";

	public static String URL = "jdbc:postgresql://localhost/gamestudio";

	public static String USER = "postgres";

	public static String PASSWORD = "pflpfl";

}
