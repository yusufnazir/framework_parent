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

public class DataSubMenu extends CustomDataTaskChange {

	private JdbcConnection connection;
	private Long id;
	private String index;
	private String type;
	private String parentMenuId;
	private String childMenuId;

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
			String query = "select id_ from sub_menus_ where id_=?";
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
				String update = "update sub_menus_ set index_=?,child_menu_id_=?,parent_menu_id_=?,type_=? where id_=?";
				prepareStatement = connection.prepareStatement(update);
				setData(prepareStatement, 1, index);
				setData(prepareStatement, 2, childMenuId);
				setData(prepareStatement, 3, parentMenuId);
				setData(prepareStatement, 4, type);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
				prepareStatement.close();
			} else {
				String insert = "insert into sub_menus_(id_,active_,index_,child_menu_id_,parent_menu_id_,type_) "
						+ "values(?,?,?,?,?,?)";
				prepareStatement = connection.prepareStatement(insert);
				setData(prepareStatement, 1, id);
				prepareStatement.setBoolean(2, true);
//				prepareStatement.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
				setData(prepareStatement, 3, index);
				setData(prepareStatement, 4, childMenuId);
				setData(prepareStatement, 5, parentMenuId);
				setData(prepareStatement, 6, type);
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
