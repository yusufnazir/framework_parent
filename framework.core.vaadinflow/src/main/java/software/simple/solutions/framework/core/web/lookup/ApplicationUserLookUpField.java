package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.web.components.LookUpField;
import software.simple.solutions.framework.core.web.view.ApplicationUserView;

public class ApplicationUserLookUpField extends LookUpField<Long, ApplicationUser> {

	private static final long serialVersionUID = 994848491488378790L;

	public ApplicationUserLookUpField() {
		super();
		setEntityClass(ApplicationUser.class);
		setViewClass(ApplicationUserView.class);
	}

}
