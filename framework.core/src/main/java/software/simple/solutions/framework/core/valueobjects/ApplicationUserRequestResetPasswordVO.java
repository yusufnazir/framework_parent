package software.simple.solutions.framework.core.valueobjects;

import java.time.LocalDateTime;

public class ApplicationUserRequestResetPasswordVO extends SuperVO {

	private static final long serialVersionUID = -8890595553328976266L;

	private Long id;
	private Long applicationUserId;
	private String key;
	private LocalDateTime validDateTime;
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationUserId() {
		return applicationUserId;
	}

	public void setApplicationUserId(Long applicationUserId) {
		this.applicationUserId = applicationUserId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public LocalDateTime getValidDateTime() {
		return validDateTime;
	}

	public void setValidDateTime(LocalDateTime validDateTime) {
		this.validDateTime = validDateTime;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
