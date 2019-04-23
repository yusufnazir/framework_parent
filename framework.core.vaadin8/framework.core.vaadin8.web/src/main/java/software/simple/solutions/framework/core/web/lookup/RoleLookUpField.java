package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.components.LookUpField;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.web.view.RoleView;

public class RoleLookUpField extends LookUpField {

	private static final long serialVersionUID = 994848491488378790L;

	public RoleLookUpField() {
		super();
		setEntityClass(Role.class);
		setViewClass(RoleView.class);
	}

}
