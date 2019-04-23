package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.ViewProperty;

public class ViewVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = ViewProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = ViewProperty.CODE)
	private StringInterval codeInterval;

	@FilterFieldProperty(fieldProperty = ViewProperty.NAME)
	private StringInterval nameInterval;

	@FilterFieldProperty(fieldProperty = ViewProperty.DESCRIPTION)
	private StringInterval descriptionInterval;

	@FilterFieldProperty(fieldProperty = ViewProperty.CLASS_NAME)
	private StringInterval viewClassNameInterval;

	private String viewClassName;

	private String code;

	private String name;

	public String getViewClassName() {
		return viewClassName;
	}

	public void setViewClassName(String viewClassName) {
		this.viewClassName = viewClassName;
	}

	public StringInterval getViewClassNameInterval() {
		return viewClassNameInterval;
	}

	public void setViewClassNameInterval(StringInterval viewClassNameInterval) {
		this.viewClassNameInterval = viewClassNameInterval;
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

}
