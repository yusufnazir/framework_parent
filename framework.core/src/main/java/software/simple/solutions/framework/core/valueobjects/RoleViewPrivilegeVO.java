package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.properties.PrivilegeProperty;
import software.simple.solutions.framework.core.properties.RoleViewPrivilegeProperty;
import software.simple.solutions.framework.core.properties.RoleViewProperty;

public class RoleViewPrivilegeVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = RoleViewPrivilegeProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = RoleViewProperty.ID)
	private Long roleViewId;

	@FilterFieldProperty(fieldProperty = PrivilegeProperty.ID)
	private Long privilegeId;

	public RoleViewPrivilegeVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getRoleViewId() {
		return roleViewId;
	}

	public void setRoleViewId(Long roleViewId) {
		this.roleViewId = roleViewId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

}
