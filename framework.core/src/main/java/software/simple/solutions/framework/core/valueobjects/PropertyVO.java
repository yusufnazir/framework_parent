package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;
import software.simple.solutions.framework.core.properties.PropertyProperty;

public class PropertyVO extends SuperVO {

	private static final long serialVersionUID = 1991750417486245358L;

	@FilterFieldProperty(fieldProperty = PropertyProperty.KEY)
	private StringInterval keyInterval;

	@FilterFieldProperty(fieldProperty = PropertyProperty.ACTIVE)
	private Boolean active;

	private Long id;
	private String key;

	public StringInterval getKeyInterval() {
		return keyInterval;
	}

	public void setKeyInterval(StringInterval keyInterval) {
		this.keyInterval = keyInterval;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
