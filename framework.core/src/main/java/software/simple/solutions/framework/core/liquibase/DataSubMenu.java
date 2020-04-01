package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataSubMenu extends CustomDataTaskChange {

	private Long id;
	private String index;
	private String type;
	private String parentMenuId;
	private String childMenuId;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from sub_menus_ where id_=?";
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
			String update = "update sub_menus_ set index_=?,child_menu_id_=?,parent_menu_id_=?,type_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, index);
				setData(prepareStatement, 2, childMenuId);
				setData(prepareStatement, 3, parentMenuId);
				setData(prepareStatement, 4, type);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into sub_menus_(id_,active_,index_,child_menu_id_,parent_menu_id_,type_) "
					+ "values(?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, index);
				setData(prepareStatement, 4, childMenuId);
				setData(prepareStatement, 5, parentMenuId);
				setData(prepareStatement, 6, type);
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getChildMenuId() {
		return childMenuId;
	}

	public void setChildMenuId(String childMenuId) {
		this.childMenuId = childMenuId;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
