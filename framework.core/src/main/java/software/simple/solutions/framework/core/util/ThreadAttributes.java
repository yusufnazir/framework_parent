package software.simple.solutions.framework.core.util;

public class ThreadAttributes {

	// private Long userId;
	private String username;
	private Long roleId;
	private Long roleCategoryId;

	public ThreadAttributes() {
		super();
	}

	// public ThreadAttributes(Long userId) {
	// this();
	// this.userId = userId;
	// }

	public ThreadAttributes(String username) {
		this();
		this.username = username;
	}

	// public ThreadAttributes(Long userId, String username) {
	// this();
	// this.userId = userId;
	// this.username = username;
	// }

	public ThreadAttributes(String username, Long roleId) {
		this(username);
		this.roleId = roleId;
	}

	public ThreadAttributes(String username, Long roleId, Long roleCategoryId) {
		this(username, roleId);
		this.roleCategoryId = roleCategoryId;
	}

	// public Long getUserId() {
	// return userId;
	// }

	// public void setUserId(Long userId) {
	// this.userId = userId;
	// }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getRoleCategoryId() {
		return roleCategoryId;
	}

	public void setRoleCategoryId(Long roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}

}
