package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.GenderProperty;

public class GenderVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private String name;
	private Boolean active;
	private String key;

	@FilterFieldProperty(fieldProperty = GenderProperty.NAME)
	private StringInterval nameInterval;

	@FilterFieldProperty(fieldProperty = GenderProperty.PROPERTY_KEY)
	private StringInterval keyInterval;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getKeyInterval() {
		return keyInterval;
	}

	public void setKeyInterval(StringInterval keyInterval) {
		this.keyInterval = keyInterval;
	}

}
