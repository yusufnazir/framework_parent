package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;
import software.simple.solutions.framework.core.constants.CxodeTables;

public class DataMailTemplate extends CustomDataTaskChange {

	private Long id;
	private String name;
	private String description;
	private String subject;
	private String message;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from " + CxodeTables.MAIL_TEMPLATE.NAME + " where id_=?";
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
			String update = "update " + CxodeTables.MAIL_TEMPLATE.NAME
					+ " set name_=?, description_=?, subject_=?, message_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, name);
				setData(prepareStatement, 2, description);
				setData(prepareStatement, 3, subject);
				setData(prepareStatement, 4, message);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into " + CxodeTables.MAIL_TEMPLATE.NAME
					+ "(id_, name_, description_, subject_, message_) " + "values(?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, name);
				setData(prepareStatement, 3, description);
				setData(prepareStatement, 4, subject);
				setData(prepareStatement, 5, message);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
