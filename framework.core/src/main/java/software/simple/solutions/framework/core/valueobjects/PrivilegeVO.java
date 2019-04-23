package software.simple.solutions.framework.core.valueobjects;

public class PrivilegeVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;

	private String code;
	private String key;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
