package sk.tuke.gamestudio.server.service.SORM;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import sk.tuke.gamestudio.server.service.annotation.Column;
import sk.tuke.gamestudio.server.service.annotation.Table;

public class SORM {
	public static final boolean DEBUG = true;

	private enum FieldType {
		ALL, PRIMARY_KEY, NOT_PRIMARY_KEY
	}

	private final Connection connection;

	public SORM(Connection connection) {
		this.connection = connection;
	}

	public String getCreateTableString(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		Table table = clazz.getAnnotation(Table.class);

		sb.append("CREATE TABLE " + table.name() + "(");

		List<Field> fields = getFields(clazz, FieldType.NOT_PRIMARY_KEY);
		List<Field> pkFields = getFields(clazz, FieldType.PRIMARY_KEY);

		for (Field field : pkFields) {
			Column column = field.getAnnotation(Column.class);
			sb.append(column.name() + " ").append(getSQLType(field.getType()) + " NOT NULL PRIMARY KEY");
		}

		for (Field field : fields) {
			sb.append(",");
			Column column = field.getAnnotation(Column.class);
			sb.append(column.name() + " ").append(getSQLType(field.getType()));
		}
		sb.append(")");
		return sb.toString();
	}

	public String getDropTableString(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		return String.format("DROP TABLE %s", table.name());
	}

	public String getInsertString(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		if (table != null) {
			List<Field> fields = getFields(clazz, FieldType.ALL);
			sb.append(table.name());
			sb.append(" (");
			sb.append(getColumnNames(fields));
			sb.append(") VALUES (");
			boolean next = false;
			for (Field field : fields) {
				if (next) {
					sb.append(",");
				}
				next = true;
				sb.append("?");
			}
			sb.append(")");
		}

		return sb.toString();
	}

	public String getSelectString(Class<?> clazz, String condition) {
		Table table = clazz.getAnnotation(Table.class);
		StringBuilder sb = new StringBuilder("SELECT ");
		if (table != null) {
			List<Field> fields = getFields(clazz, FieldType.ALL);
			sb.append(getColumnNames(fields));
			sb.append(" FROM ");
			sb.append(table.name());
			if (condition != null) {
				sb.append(" WHERE " + condition);
			}
		}
		return sb.toString();
	}

	public String getSingleSelectString(Class<?> clazz, String requiredValue, String condition) {
		Table table = clazz.getAnnotation(Table.class);
		StringBuilder sb = new StringBuilder("SELECT ");
		if (table != null) {
			sb.append(requiredValue + " FROM ");
			sb.append(table.name());
			if (condition != null) {
				sb.append(" WHERE " + condition);
			}
		}
		return sb.toString();
	}

	public String getUpdateString(Class<?> clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + clazz.getSimpleName() + " SET ");

		boolean notFirst = false;
		for (Field field : clazz.getDeclaredFields()) {
			if (notFirst)
				sb.append(", ");
			notFirst = true;
			sb.append(field.getName() + "= ?");
		}

		return sb.toString();
	}

	private String getSQLType(Class<?> clazz) {
		switch (clazz.getSimpleName()) {
		case "String":
			return "VARCHAR(64)";
		case "int":
			return "INTEGER";
		case "Date":
			return "DATE";
		default:
			throw new IllegalArgumentException("Un. java type " + clazz.getCanonicalName());
		}
	}

	private <T> List<Field> getFields(Class<T> cl, FieldType fieldType) {
		List<Field> fields = new ArrayList<Field>();

		for (Field field : cl.getDeclaredFields()) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				switch (fieldType) {
				case ALL:
					fields.add(field);
					break;
				case PRIMARY_KEY:
					if (column.primaryKey()) {
						fields.add(field);
					}
					break;
				case NOT_PRIMARY_KEY:
					if (!column.primaryKey()) {
						fields.add(field);
					}
					break;
				}
			}
		}

		return fields;
	}

	private String getColumnNames(List<Field> fields) {
		StringBuilder sb = new StringBuilder();
		boolean next = false;

		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			if (next) {
				sb.append(",");
			}
			next = true;

			if (column != null) {
				sb.append(column.name());
			}
		}

		return sb.toString();
	}
}
