package software.simple.solutions.framework.core.web.view.forms;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.UserRoleProperty;
import software.simple.solutions.framework.core.valueobjects.UserRoleVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.lookup.ApplicationUserLookUpField;
import software.simple.solutions.framework.core.web.lookup.RoleLookUpField;

public class UserRoleForm extends FormView {

	private static final long serialVersionUID = 8487088559806386576L;
	private CFormLayout formGrid;
	private ApplicationUserLookUpField userFld;
	private RoleLookUpField roleFld;
	private CCheckBox activeFld;

	private UserRole userRole;

	public UserRoleForm() {
		super();
	}

	@Override
	public void executeBuild() {
		createFormGrid();
	}

	private CFormLayout createFormGrid() {
		Panel formCard = new Panel();
		add(formCard);
		formGrid = new CFormLayout();
		formCard.add(formGrid);
		formCard.setMaxWidth("400px");

		userFld = formGrid.add(ApplicationUserLookUpField.class, UserRoleProperty.USER);
		userFld.setMaxWidth("400px");

		roleFld = formGrid.add(RoleLookUpField.class, UserRoleProperty.ROLE);
		roleFld.setMaxWidth("400px");

		activeFld = formGrid.add(CCheckBox.class, UserRoleProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserRole setFormValues(Object entity) throws FrameworkException {
		userRole = (UserRole) entity;
		activeFld.setValue(userRole.getActive());
		userFld.setValue(userRole.getApplicationUser());
		roleFld.setValue(userRole.getRole());

		return userRole;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		activeFld.setValue(true);

		Object parentEntity = getParentEntity();
		if (parentEntity instanceof ApplicationUser) {
			ApplicationUser applicationUser = (ApplicationUser) parentEntity;
			userFld.setValue(applicationUser);
			userFld.setEnabled(false);
		}
		if (parentEntity instanceof Role) {
			Role role = (Role) parentEntity;
			roleFld.setValue(role);
			roleFld.setEnabled(false);
		}

	}

	@Override
	public Object getFormValues() throws FrameworkException {
		UserRoleVO vo = new UserRoleVO();

		vo.setId(userRole == null ? null : userRole.getId());

		vo.setActive(activeFld.getValue());
		vo.setUserId(userFld.getItemId());
		vo.setRoleId(roleFld.getItemId());

		return vo;
	}

}
