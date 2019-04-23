package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.RoleCategoryProperty;

public class RoleCategoryVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = RoleCategoryProperty.NAME)
	private StringInterval nameInterval;

	private String name;

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
