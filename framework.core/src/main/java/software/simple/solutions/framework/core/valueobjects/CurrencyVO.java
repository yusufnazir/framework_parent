package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.CurrencyProperty;

public class CurrencyVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private String code;
	private String name;

	@FilterFieldProperty(fieldProperty = CurrencyProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = CurrencyProperty.CODE)
	private StringInterval codeInterval;

	@FilterFieldProperty(fieldProperty = CurrencyProperty.NAME)
	private StringInterval nameInterval;

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

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getCodeInterval() {
		return codeInterval;
	}

	public void setCodeInterval(StringInterval codeInterval) {
		this.codeInterval = codeInterval;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
