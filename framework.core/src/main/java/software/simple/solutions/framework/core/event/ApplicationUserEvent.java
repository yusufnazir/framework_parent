package software.simple.solutions.framework.core.event;

import org.springframework.context.ApplicationEvent;

public class ApplicationUserEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6523185983035906081L;

	private boolean isNew = false;

	public ApplicationUserEvent(Object source, Boolean isNew) {
		super(source);
		this.isNew = isNew;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}
