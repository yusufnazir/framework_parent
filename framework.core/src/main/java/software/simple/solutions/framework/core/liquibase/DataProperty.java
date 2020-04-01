package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataProperty extends CustomDataTaskChange {

	private Long id;
	private String key;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		boolean exists = false;
		String query = "select id_ from properties_ where id_=?";
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			setData(prepareStatement, 1, id);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {

				while (resultSet.next()) {
					exists = true;
				}
			}
		}

		if (exists) {
			String update = "update properties_ set key_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, key);
				setData(prepareStatement, 2, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into properties_(id_,active_,key_) " + "values(?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, key);
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
