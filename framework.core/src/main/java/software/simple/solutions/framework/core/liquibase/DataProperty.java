package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public class DataProperty extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String key;

	@Override
	public String getConfirmationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUp() throws SetupException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFileOpener(ResourceAccessor resourceAccessor) {
		// TODO Auto-generated method stub

	}

	@Override
	public ValidationErrors validate(Database database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Database database) throws CustomChangeException {
		connection = (JdbcConnection) database.getConnection();

		try {
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
					prepareStatement.setBoolean(2, true);
					setData(prepareStatement, 3, key);
					prepareStatement.executeUpdate();
				}
			}

		} catch (DatabaseException | SQLException e) {
			e.printStackTrace();
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
