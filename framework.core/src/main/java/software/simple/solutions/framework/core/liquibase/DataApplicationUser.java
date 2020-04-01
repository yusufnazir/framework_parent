package software.simple.solutions.framework.core.liquibase;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import liquibase.exception.DatabaseException;

public class DataApplicationUser extends CustomDataTaskChange {

	private Long id;
	private String password;
	private String username;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from application_users_ where id_=?";
		boolean exists = false;
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			setData(prepareStatement, 1, id);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {

				while (resultSet.next()) {
					exists = true;
				}
			}
		}

		if (exists) {
			String update = "update application_users_ set password_=?, username_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, password);
				setData(prepareStatement, 2, username);
				setData(prepareStatement, 3, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into application_users_(id_,active_,password_,password_change_date_,force_change_password_,username_) "
					+ "values(?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, password);
				setData(prepareStatement, 4, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 5, false);
				setData(prepareStatement, 6, username);
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
