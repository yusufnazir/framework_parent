package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.RelationTypeProperty;

public class RelationTypeVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	@FilterFieldProperty(fieldProperty = RelationTypeProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = RelationTypeProperty.CODE)
	private StringInterval codeInterval;

	@FilterFieldProperty(fieldProperty = RelationTypeProperty.NAME)
	private StringInterval nameInterval;

	private String code;
	private String name;

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
