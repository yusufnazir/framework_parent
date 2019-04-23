package software.simple.solutions.framework.core.valueobjects;

public class CountryVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;

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

}
