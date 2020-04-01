package software.simple.solutions.framework.core.liquibase;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import liquibase.exception.DatabaseException;

public class DataPerson extends CustomDataTaskChange {

	private Long id;
	private String code;
	private String firstName;
	private String lastName;
	private Long genderId;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from persons_ where id_=?";
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
			String update = "update persons_ set code_=?, first_name_=?,last_name_=?,gender_id_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, firstName);
				setData(prepareStatement, 3, lastName);
				setData(prepareStatement, 4, genderId);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into persons_(id_,active_,code_,first_name_,last_name_,date_of_birth_,gender_id_) "
					+ "values(?,?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, code);
				setData(prepareStatement, 4, firstName);
				setData(prepareStatement, 5, lastName);
				setData(prepareStatement, 6, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 7, genderId);
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

}
