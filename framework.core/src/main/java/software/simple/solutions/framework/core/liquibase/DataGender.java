package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataGender extends CustomDataTaskChange {

	private Long id;
	private String name;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		boolean exists = false;
		String query = "select id_ from genders_ where id_=?";
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			setData(prepareStatement, 1, id);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {
				while (resultSet.next()) {
					exists = true;
				}
			}
		}

		if (exists) {
			String update = "update genders_ set name_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, name);
				setData(prepareStatement, 2, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into genders_(id_,active_,name_) " + "values(?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, name);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
