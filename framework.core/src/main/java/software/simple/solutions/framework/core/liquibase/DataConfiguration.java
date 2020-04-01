package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;
import software.simple.solutions.framework.core.constants.CxodeTables;

public class DataConfiguration extends CustomDataTaskChange {

	private Long id;
	private String code;
	private String name;
	private String value;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		boolean exists = false;
		String query = "select id_ from " + CxodeTables.CONFIGURATION.NAME + " where "
				+ CxodeTables.CONFIGURATION.COLUMNS.ID + "=?";
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			setData(prepareStatement, 1, id);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {

				while (resultSet.next()) {
					exists = true;
				}
			}
		}

		if (exists) {
			String update = "update " + CxodeTables.CONFIGURATION.NAME + " set "
					+ CxodeTables.CONFIGURATION.COLUMNS.CODE + "=?, " + CxodeTables.CONFIGURATION.COLUMNS.NAME + "=?,"
					+ CxodeTables.CONFIGURATION.COLUMNS.VALUE + "=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, name);
				setData(prepareStatement, 3, value);
				setData(prepareStatement, 4, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into " + CxodeTables.CONFIGURATION.NAME + "(" + CxodeTables.CONFIGURATION.COLUMNS.ID
					+ "," + CxodeTables.CONFIGURATION.COLUMNS.ACTIVE + ",code_,name_,value_) " + "values(?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, code);
				setData(prepareStatement, 4, name);
				setData(prepareStatement, 5, value);
				prepareStatement.executeUpdate();
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
