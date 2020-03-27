package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;

public class PropertyPerLocaleVO extends SuperVO {

	private static final long serialVersionUID = 1991750417486245358L;

	// @FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.PROPERTY)
	// private StringInterval propertyInterval;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.VALUE)
	private StringInterval valueInterval;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.REFERENCE_KEY)
	private StringInterval referenceKeyInterval;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.REFERENCE_ID)
	private StringInterval referenceIdInterval;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.REFERENCE_ID)
	private String referenceId;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.LANGUAGE)
	private Long languageId;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.ACTIVE)
	private Boolean active;

	private Long id;
	private String value;
	private String referenceKey;
	// private String property;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	// public StringInterval getPropertyInterval() {
	// return propertyInterval;
	// }

	// public void setPropertyInterval(StringInterval propertyInterval) {
	// this.propertyInterval = propertyInterval;
	// }

	public StringInterval getValueInterval() {
		return valueInterval;
	}

	public void setValueInterval(StringInterval valueInterval) {
		this.valueInterval = valueInterval;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public StringInterval getReferenceKeyInterval() {
		return referenceKeyInterval;
	}

	public void setReferenceKeyInterval(StringInterval referenceKeyInterval) {
		this.referenceKeyInterval = referenceKeyInterval;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceKey() {
		return referenceKey;
	}

	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}

	public StringInterval getReferenceIdInterval() {
		return referenceIdInterval;
	}

	public void setReferenceIdInterval(StringInterval referenceIdInterval) {
		this.referenceIdInterval = referenceIdInterval;
	}

	// public String getProperty() {
	// return property;
	// }

	// public void setProperty(String property) {
	// this.property = property;
	// }

}
