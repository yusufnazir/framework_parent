package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataCountry extends CustomDataTaskChange {

	private Long id;
	private String name;
	private String alpha2;
	private String alpha3;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		boolean exists = false;
		String query = "select id_ from countries_ where id_=?";
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			setData(prepareStatement, 1, id);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {

				while (resultSet.next()) {
					exists = true;
				}
			}
		}

		if (exists) {
			String update = "update countries_ set name_=?,alpha2_=?, alpha3_=?,active_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, name);
				setData(prepareStatement, 2, alpha2);
				setData(prepareStatement, 3, alpha3);
				setData(prepareStatement, 4, true);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into countries_(name_,alpha2_,alpha3_,active_,id_) " + "values(?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, name);
				setData(prepareStatement, 2, alpha2);
				setData(prepareStatement, 3, alpha3);
				setData(prepareStatement, 4, true);
				setData(prepareStatement, 5, id);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlpha2() {
		return alpha2;
	}

	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}

	public String getAlpha3() {
		return alpha3;
	}

	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}

}
