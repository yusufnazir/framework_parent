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

public class DataGender extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String name;

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
					prepareStatement.setBoolean(2, true);
					setData(prepareStatement, 3, name);
					prepareStatement.executeUpdate();
				}
			}

		} catch (DatabaseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
