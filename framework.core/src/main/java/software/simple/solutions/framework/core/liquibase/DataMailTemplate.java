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
import software.simple.solutions.framework.core.constants.CxodeTables;

public class DataMailTemplate extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String name;
	private String description;
	private String subject;
	private String message;

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
			String query = "select id_ from " + CxodeTables.MAIL_TEMPLATE.NAME + " where id_=?";
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
				String update = "update " + CxodeTables.MAIL_TEMPLATE.NAME
						+ " set name_=?, description_=?, subject_=?, message_=? where id_=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, name);
				setData(prepareStatement, 2, description);
				setData(prepareStatement, 3, subject);
				setData(prepareStatement, 4, message);
//				prepareStatement.setDate(5, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into " + CxodeTables.MAIL_TEMPLATE.NAME
						+ "(id_, name_, description_, subject_, message_) " + "values(?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, name);
				setData(prepareStatement, 3, description);
				setData(prepareStatement, 4, subject);
				setData(prepareStatement, 5, message);
//				prepareStatement.setDate(6, new Date(Calendar.getInstance().getTime().getTime()));
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
