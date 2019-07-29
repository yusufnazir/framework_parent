package software.simple.solutions.framework.core.pojo;

public class EntityUpdate {

	private boolean isNew;
	private Object entity;

	public EntityUpdate() {
		super();
	}

	public EntityUpdate(Object entity) {
		this();
		this.entity = entity;
		this.isNew = true;
	}

	public EntityUpdate(Object entity, boolean isNew) {
		this();
		this.entity = entity;
		this.isNew = isNew;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

}
