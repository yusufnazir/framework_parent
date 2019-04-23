package software.simple.solutions.framework.core.liquibase;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public class DataApplicationUser extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String password;
	private String username;

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
			String query = "select id_ from application_users_ where id_=?";
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			setData(prepareStatement, 1, id);
			ResultSet resultSet = prepareStatement.executeQuery();

			boolean exists = false;
			while (resultSet.next()) {
				exists = true;
			}
			resultSet.close();
			prepareStatement.close();

			if (exists) {
				String update = "update application_users_ set password_=?, username_=? where id_=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, password);
				setData(prepareStatement, 2, username);
				setData(prepareStatement, 3, id);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into application_users_(id_,active_,password_,password_change_date_,force_change_password_,username_) "
						+ "values(?,?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, id);
				prepareStatement.setBoolean(2, true);
//				prepareStatement.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 3, password);
				prepareStatement.setDate(4, new Date(Calendar.getInstance().getTime().getTime()));
				prepareStatement.setBoolean(5, false);
				setData(prepareStatement, 6, username);
				prepareStatement.executeUpdate();
				prepareStatement.close();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
