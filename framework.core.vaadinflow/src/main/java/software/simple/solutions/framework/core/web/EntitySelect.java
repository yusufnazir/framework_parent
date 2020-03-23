package software.simple.solutions.framework.core.web;

public class EntitySelect {

	private Object entity;

	public EntitySelect() {
		super();
	}

	public EntitySelect(Object entity) {
		this();
		this.entity = entity;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

}
