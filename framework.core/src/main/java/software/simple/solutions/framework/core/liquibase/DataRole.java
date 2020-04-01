package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataRole extends CustomDataTaskChange {

	private Long id;
	private String code;
	private String description;
	private String name;
	private String roleCategoryId;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from roles_ where id_=?";
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
			String update = "update roles_ set code_=?, description_=?, name_=?, role_category_id_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, description);
				setData(prepareStatement, 3, name);
				setData(prepareStatement, 4, roleCategoryId);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into roles_(id_,active_,code_,description_,name_,role_category_id_) "
					+ "values(?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, code);
				setData(prepareStatement, 4, description);
				setData(prepareStatement, 5, name);
				setData(prepareStatement, 6, roleCategoryId);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCategoryId() {
		return roleCategoryId;
	}

	public void setRoleCategoryId(String roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}

}
