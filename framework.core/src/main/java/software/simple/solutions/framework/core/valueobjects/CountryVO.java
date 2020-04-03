package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.CountryProperty;

public class CountryVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	@FilterFieldProperty(fieldProperty = CountryProperty.ACTIVE)
	private Boolean active;

	@FilterFieldProperty(fieldProperty = CountryProperty.NAME)
	private StringInterval nameInterval;

	@FilterFieldProperty(fieldProperty = CountryProperty.ALPHA2)
	private StringInterval alpha2Interval;

	@FilterFieldProperty(fieldProperty = CountryProperty.ALPHA3)
	private StringInterval alpha3Interval;

	private Long id;
	private String name;
	private String alpha2;
	private String alpha3;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getAlpha2Interval() {
		return alpha2Interval;
	}

	public void setAlpha2Interval(StringInterval alpha2Interval) {
		this.alpha2Interval = alpha2Interval;
	}

	public StringInterval getAlpha3Interval() {
		return alpha3Interval;
	}

	public void setAlpha3Interval(StringInterval alpha3Interval) {
		this.alpha3Interval = alpha3Interval;
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

	public String getAlpha2() {
		return alpha2;
	}

	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}

	public String getAlpha3() {
		return alpha3;
	}

	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}

}
