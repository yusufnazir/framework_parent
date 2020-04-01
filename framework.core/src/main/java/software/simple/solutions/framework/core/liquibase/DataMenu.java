package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataMenu extends CustomDataTaskChange {

	private Long id;
	private String code;
	private String description;
	private String name;
	private String viewId;
	private String index;
	private String type;
	private String parentMenuId;
	private String icon;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {

		String query = "select id_ from menus_ where id_=?";
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
			String update = "update menus_ set code_=?, description_=?,name_=?,view_id_=?,index_=?,parent_menu_id_=?,type_=?, icon_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, code);
				setData(prepareStatement, 2, description);
				setData(prepareStatement, 3, name);
				setData(prepareStatement, 4, viewId);
				setData(prepareStatement, 5, index);
				setData(prepareStatement, 6, parentMenuId);
				setData(prepareStatement, 7, type);
				setData(prepareStatement, 8, icon);
				setData(prepareStatement, 9, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into menus_(id_,active_,code_,description_,name_,view_id_,index_,parent_menu_id_,type_,icon_) "
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, code);
				setData(prepareStatement, 4, description);
				setData(prepareStatement, 5, name);
				setData(prepareStatement, 6, viewId);
				setData(prepareStatement, 7, index);
				setData(prepareStatement, 8, parentMenuId);
				setData(prepareStatement, 9, type);
				setData(prepareStatement, 10, icon);
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

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
