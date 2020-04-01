package software.simple.solutions.framework.core.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.exception.DatabaseException;

public class DataMessageLocale extends CustomDataTaskChange {

	private Long id;
	private Long localeId;
	private Long messageId;
	private String subject;
	private String reason;
	private String remedy;

	@Override
	public void handleUpdate() throws DatabaseException, SQLException {
		String query = "select id_ from messages_per_locales_ where id_=?";
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
			String update = "update messages_per_locales_ set subject_=?,reason_=?,remedy_=?, locale_id_=?, message_id_=? where id_=?";
			try (PreparedStatement prepareStatement = connection.prepareStatement(update)) {
				setData(prepareStatement, 1, subject);
				setData(prepareStatement, 2, reason);
				setData(prepareStatement, 3, remedy);
				setData(prepareStatement, 4, localeId);
				setData(prepareStatement, 5, messageId);
				setData(prepareStatement, 6, id);
				prepareStatement.executeUpdate();
			}
		} else {
			String insert = "insert into messages_per_locales_(id_,active_,locale_id_,message_id_,subject_,reason_,remedy_) "
					+ "values(?,?,?,?,?,?,?)";
			try (PreparedStatement prepareStatement = connection.prepareStatement(insert)) {
				setData(prepareStatement, 1, id);
				setData(prepareStatement, 2, true);
				setData(prepareStatement, 3, localeId);
				setData(prepareStatement, 4, messageId);
				setData(prepareStatement, 5, subject);
				setData(prepareStatement, 6, reason);
				setData(prepareStatement, 7, remedy);
				prepareStatement.executeUpdate();
			}
		}
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemedy() {
		return remedy;
	}

	public void setRemedy(String remedy) {
		this.remedy = remedy;
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

}
