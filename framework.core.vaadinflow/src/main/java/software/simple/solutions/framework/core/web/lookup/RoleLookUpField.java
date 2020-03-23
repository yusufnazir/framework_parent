package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.web.LookUpField;
import software.simple.solutions.framework.core.web.view.RoleView;

public class RoleLookUpField extends LookUpField<Long, Role> {

	private static final long serialVersionUID = 994848491488378790L;

	public RoleLookUpField() {
		super();
		setEntityClass(Role.class);
		setViewClass(RoleView.class);
	}

}
