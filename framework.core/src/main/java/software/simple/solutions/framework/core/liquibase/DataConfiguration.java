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

public class DataConfiguration extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String code;
	private String name;
	private String value;

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
			String query = "select id_ from " + CxodeTables.CONFIGURATION.NAME + " where " + CxodeTables.CONFIGURATION.COLUMNS.ID
					+ "=?";
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
				String update = "update " + CxodeTables.CONFIGURATION.NAME + " set " + CxodeTables.CONFIGURATION.COLUMNS.CODE
						+ "=?, " + CxodeTables.CONFIGURATION.COLUMNS.NAME + "=?," + CxodeTables.CONFIGURATION.COLUMNS.VALUE
						+ "=? where id_=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, name);
				setData(prepareStatement, 3, value);
				setData(prepareStatement, 4, id);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into " + CxodeTables.CONFIGURATION.NAME + "(" + CxodeTables.CONFIGURATION.COLUMNS.ID + ","
						+ CxodeTables.CONFIGURATION.COLUMNS.ACTIVE + ",code_,name_,value_) "
						+ "values(?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, id);
				prepareStatement.setBoolean(2, true);
//				prepareStatement.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 3, code);
				setData(prepareStatement, 4, name);
				setData(prepareStatement, 5, value);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			}

		} catch (DatabaseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JdbcConnection getConnection() {
		return connection;
	}

	public void setConnection(JdbcConnection connection) {
		this.connection = connection;
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
