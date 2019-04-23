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

public class DataMenu extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String key;
	private String code;
	private String description;
	private String name;
	private String viewId;
	private String index;
	private String type;
	private String parentMenuId;

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
			String query = "select id_ from menus_ where id_=?";
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
				String update = "update menus_ set key_=?, code_=?, description_=?,name_=?,view_id_=?,index_=?,parent_menu_id_=?,type_=? where id_=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, key);
				setData(prepareStatement, 2, code);
				setData(prepareStatement, 3, description);
				setData(prepareStatement, 4, name);
				setData(prepareStatement, 5, viewId);
				setData(prepareStatement, 6, index);
				setData(prepareStatement, 7, parentMenuId);
				setData(prepareStatement, 8, type);
				setData(prepareStatement, 9, id);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into menus_(id_,active_,key_,code_,description_,name_,view_id_,index_,parent_menu_id_,type_) "
						+ "values(?,?,?,?,?,?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, id);
				prepareStatement.setBoolean(2, true);
//				prepareStatement.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 3, key);
				setData(prepareStatement, 4, code);
				setData(prepareStatement, 5, description);
				setData(prepareStatement, 6, name);
				setData(prepareStatement, 7, viewId);
				setData(prepareStatement, 8, index);
				setData(prepareStatement, 9, parentMenuId);
				setData(prepareStatement, 10, type);
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

}
