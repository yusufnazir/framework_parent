package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import liquibase.change.custom.CustomTaskChange;

public abstract class CustomDataTaskChange implements CustomTaskChange {

	protected void setData(PreparedStatement prepareStatement, int parameterIndex, Object x) throws SQLException {
		if (x == null) {
			prepareStatement.setNull(parameterIndex, Types.VARCHAR);
		} else {
			if (x instanceof String) {
				if (((String) x).trim().isEmpty()) {
					prepareStatement.setNull(parameterIndex, Types.VARCHAR);
				} else {
					prepareStatement.setString(parameterIndex, (String) x);
				}
			} else if (x instanceof Long) {
				if ((long) x == -1) {
					prepareStatement.setNull(parameterIndex, Types.NUMERIC);
				} else {
					prepareStatement.setLong(parameterIndex, (long) x);
				}
			} else {
				prepareStatement.setObject(parameterIndex, x);
			}
		}
	}
}
