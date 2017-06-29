package sk.tuke.gamestudio.server.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ServiceSORM {

	public String getInsertString(Class<?> clazz) {
		return String.format("INSERT INTO %s (%s) VALUES (%s)", clazz.getSimpleName(),
				Arrays.stream(clazz.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.joining(", ")),
				Arrays.stream(clazz.getDeclaredFields()).map(f -> "?").collect(Collectors.joining(", ")));
	}
	

	public String getSelectString(Class<?> clazz) {
		return String.format("SELECT %s FROM %s",
				Arrays.stream(clazz.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.joining(", ")),
				clazz.getSimpleName());
	}
	

//	public String getUpdateString(Class<?> clazz) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("UPDATE " + clazz.getSimpleName() + " SET ");
//
//		boolean notFirst = false;
//		for (Field field : clazz.getDeclaredFields()) {
//			if (notFirst)
//				sb.append(", ");
//			notFirst = true;
//			sb.append(field.getName() + "= ?");
//		}
//
//		return sb.toString();
//	}

}
