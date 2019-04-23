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

public class DataPrivilege extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String code;
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
			String query = "select id_ from privileges_ where id_=?";
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
				String update = "update privileges_ set code_=?,key_=? where id_=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, key);
				setData(prepareStatement, 3, id);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into privileges_(id_,code_,key_) " + "values(?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, code);
				setData(prepareStatement, 3, key);
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

	public String getPrivilege() {
		return code;
	}

	public void setPrivilege(String privilege) {
		this.code = privilege;
	}

	public String getRoleId() {
		return key;
	}

	public void setRoleId(String roleId) {
		this.key = roleId;
	}

}
