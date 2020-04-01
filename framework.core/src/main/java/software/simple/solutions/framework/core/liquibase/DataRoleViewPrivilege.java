package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataRoleViewPrivilege extends CustomDataTaskChange {

	private Long id;
	private String privilegeId;
	private String roleViewId;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from role_view_privileges_ where id_=?";
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
			String update = "update role_view_privileges_ set privilege_id_=?,role_view_id_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, privilegeId);
				setData(prepareStatement, 2, roleViewId);
				setData(prepareStatement, 3, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into role_view_privileges_(id_,privilege_id_,role_view_id_) " + "values(?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, privilegeId);
				setData(prepareStatement, 3, roleViewId);
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

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getRoleViewId() {
		return roleViewId;
	}

	public void setRoleViewId(String roleViewId) {
		this.roleViewId = roleViewId;
	}

}
