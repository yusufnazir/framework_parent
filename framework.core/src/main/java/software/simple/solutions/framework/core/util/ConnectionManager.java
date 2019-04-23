package software.simple.solutions.framework.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private String url;
	private String user;
	private String password;
	private String driverClass;
	private Connection connection;

	public ConnectionManager(String driverClass, String url, String user, String password) {
		this.driverClass = driverClass;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Expecting connection to be closed.");
		}
	}

}
