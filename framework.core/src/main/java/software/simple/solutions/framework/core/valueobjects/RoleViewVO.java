package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.properties.RoleViewProperty;
import software.simple.solutions.framework.core.properties.ViewProperty;

public class RoleViewVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = RoleViewProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = RoleProperty.ID)
	private Long roleId;

	@FilterFieldProperty(fieldProperty = ViewProperty.ID)
	private Long viewId;

	public RoleViewVO() {
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

}
