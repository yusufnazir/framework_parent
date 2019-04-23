package software.simple.solutions.framework.core.valueobjects;

public class SubMenuVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private Boolean active;
	private Long parentMenuId;
	private Long childMenuId;
	private Long type;
	private Long index;

	public SubMenuVO() {
		super();
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

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Long getChildMenuId() {
		return childMenuId;
	}

	public void setChildMenuId(Long childMenuId) {
		this.childMenuId = childMenuId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

}
