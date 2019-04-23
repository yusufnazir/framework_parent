package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.properties.UserRoleProperty;

public class UserRoleVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = UserRoleProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = UserRoleProperty.USER)
	private StringInterval userInterval;

	@FilterFieldProperty(fieldProperty = UserRoleProperty.ROLE)
	private StringInterval roleInterval;

	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.ID)
	private Long userId;

	@FilterFieldProperty(fieldProperty = RoleProperty.ID)
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public StringInterval getUserInterval() {
		return userInterval;
	}

	public void setUserInterval(StringInterval userInterval) {
		this.userInterval = userInterval;
	}

	public StringInterval getRoleInterval() {
		return roleInterval;
	}

	public void setRoleInterval(StringInterval roleInterval) {
		this.roleInterval = roleInterval;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
