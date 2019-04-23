package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.RoleProperty;

public class RoleVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = RoleProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = RoleProperty.CODE)
	private StringInterval codeInterval;

	@FilterFieldProperty(fieldProperty = RoleProperty.NAME)
	private StringInterval nameInterval;

	@FilterFieldProperty(fieldProperty = RoleProperty.DESCRIPTION)
	private StringInterval descriptionInterval;

	private String code;

	private String name;

	private String description;

	private Long roleCategoryId;

	public StringInterval getCodeInterval() {
		return codeInterval;
	}

	public void setCodeInterval(StringInterval codeInterval) {
		this.codeInterval = codeInterval;
	}

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getDescriptionInterval() {
		return descriptionInterval;
	}

	public void setDescriptionInterval(StringInterval descriptionInterval) {
		this.descriptionInterval = descriptionInterval;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRoleCategoryId() {
		return roleCategoryId;
	}

	public void setRoleCategoryId(Long roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}

}
