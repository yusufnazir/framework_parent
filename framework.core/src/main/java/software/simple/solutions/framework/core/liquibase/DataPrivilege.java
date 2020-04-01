package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataPrivilege extends CustomDataTaskChange {

	private Long id;
	private String code;
	private String key;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from privileges_ where id_=?";
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
			String update = "update privileges_ set code_=?,key_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, key);
				setData(prepareStatement, 3, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into privileges_(id_,code_,key_) " + "values(?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, code);
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
