package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.properties.PrivilegeProperty;
import software.simple.solutions.framework.core.properties.RolePrivilegeProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;

public class RolePrivilegeVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = RolePrivilegeProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = RoleProperty.ID)
	private Long roleId;

	@FilterFieldProperty(fieldProperty = PrivilegeProperty.ID)
	private Long privilegeId;

	public RolePrivilegeVO() {
		super();
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
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

	// public Privilege getPrivilege() {
	// return privilege;
	// }

	// public void setPrivilege(Privilege privilege) {
	// this.privilege = privilege;
	// }

}
