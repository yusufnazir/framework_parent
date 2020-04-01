package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataPropertyLocale extends CustomDataTaskChange {

	private Long id;
	private Long localeId;
	private String referenceKey;
	private String referenceId;
	private String value;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from properties_per_locales_ where id_=?";
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
			String update = "update properties_per_locales_ set value_=?, locale_id_=?, reference_key_=?, reference_id_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, value);
				setData(prepareStatement, 2, localeId);
				setData(prepareStatement, 3, referenceKey);
				setData(prepareStatement, 4, referenceId);
				setData(prepareStatement, 5, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into properties_per_locales_(id_,active_,value_,locale_id_,reference_key_,reference_id_) "
					+ "values(?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, value);
				setData(prepareStatement, 4, localeId);
				setData(prepareStatement, 5, referenceKey);
				setData(prepareStatement, 6, referenceId);
				prepareStatement.executeUpdate();
			}
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLocaleId() {
		return localeId;
	}

	public void setLocaleId(Long localeId) {
		this.localeId = localeId;
	}

	public String getReferenceKey() {
		return referenceKey;
	}

	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}
