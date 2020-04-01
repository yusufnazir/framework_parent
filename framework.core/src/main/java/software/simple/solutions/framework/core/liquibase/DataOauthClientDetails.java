package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataOauthClientDetails extends CustomDataTaskChange {

	private String clientId, clientSecret, scope, authorizedGrantTypes, authorities, accessTokenValidity,
			refreshTokenValidity;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select client_id from oauth_client_details where client_id=?";
		boolean exists = false;
		try (PreparedStatement prepareStatement = connection.prepareStatement(query)) {
			setData(prepareStatement, 1, clientId);
			try (ResultSet resultSet = prepareStatement.executeQuery()) {
				while (resultSet.next()) {
					exists = true;
				}
			}
		}

		if (exists) {
			String update = "update oauth_client_details set client_secret=?, scope=?, authorized_grant_types=?, authorities=?, access_token_validity=?, refresh_token_validity=?  where client_id=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, clientSecret);
				setData(prepareStatement, 2, scope);
				setData(prepareStatement, 3, authorizedGrantTypes);
				setData(prepareStatement, 4, authorities);
				setData(prepareStatement, 5, accessTokenValidity);
				setData(prepareStatement, 6, refreshTokenValidity);
				setData(prepareStatement, 7, clientId);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into oauth_client_details(client_secret,scope,authorized_grant_types,authorities,access_token_validity,refresh_token_validity,client_id) "
					+ "values(?,?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, clientSecret);
				setData(prepareStatement, 2, scope);
				setData(prepareStatement, 3, authorizedGrantTypes);
				setData(prepareStatement, 4, authorities);
				setData(prepareStatement, 5, accessTokenValidity);
				setData(prepareStatement, 6, refreshTokenValidity);
				setData(prepareStatement, 7, clientId);
				prepareStatement.executeUpdate();
			}
		}
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public String getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(String accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public String getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(String refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

}
