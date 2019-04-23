package software.simple.solutions.framework.core.valueobjects;

public class StreetVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	
	private Boolean active;
	
	private String name;

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

}
