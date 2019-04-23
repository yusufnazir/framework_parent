package software.simple.solutions.framework.core.valueobjects;

public class ApplicationUserConfigurationVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Long applicationUserId;
	private String code;
	private String name;
	private String value;
	private String bigValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBigValue() {
		return bigValue;
	}

	public void setBigValue(String bigValue) {
		this.bigValue = bigValue;
	}

	public Long getApplicationUserId() {
		return applicationUserId;
	}

	public void setApplicationUserId(Long applicationUserId) {
		this.applicationUserId = applicationUserId;
	}

}
