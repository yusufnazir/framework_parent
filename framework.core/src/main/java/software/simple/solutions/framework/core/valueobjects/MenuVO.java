package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.MenuProperty;
import software.simple.solutions.framework.core.properties.ViewProperty;

public class MenuVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = MenuProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = MenuProperty.CODE)
	private StringInterval codeInterval;

	@FilterFieldProperty(fieldProperty = MenuProperty.NAME)
	private StringInterval nameInterval;

	@FilterFieldProperty(fieldProperty = MenuProperty.DESCRIPTION)
	private StringInterval descriptionInterval;

	@FilterFieldProperty(fieldProperty = ViewProperty.ID)
	private Long viewId;

	@FilterFieldProperty(fieldProperty = MenuProperty.PARENT_MENU)
	private Long parentMenuId;

	@FilterFieldProperty(fieldProperty = MenuProperty.TYPE)
	private Long type;

	private String code;

	private String name;

	private String key;

	public MenuVO() {
		super();
	}

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

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

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
