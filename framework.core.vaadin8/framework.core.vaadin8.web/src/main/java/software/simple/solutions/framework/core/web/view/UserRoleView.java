package software.simple.solutions.framework.core.web.view;

import com.vaadin.data.ValueProvider;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.UserRoleProperty;
import software.simple.solutions.framework.core.service.IUserRoleService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.UserRoleVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.lookup.ApplicationUserLookUpField;
import software.simple.solutions.framework.core.web.lookup.RoleLookUpField;

public class UserRoleView extends BasicTemplate<UserRole> {

	private static final long serialVersionUID = 6503015064562511801L;

	public UserRoleView() {
		setEntityClass(UserRole.class);
		setServiceClass(IUserRoleService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(new ValueProvider<UserRole, String>() {

			@Override
			public String apply(UserRole source) {
				return source.getApplicationUser() == null ? null : source.getApplicationUser().getCaption();
			}
		}, UserRoleProperty.USER);
		addContainerProperty(new ValueProvider<UserRole, String>() {

			@Override
			public String apply(UserRole source) {
				return source.getRole() == null ? null : source.getRole().getCaption();
			}
		}, UserRoleProperty.ROLE);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private ApplicationUserLookUpField applicationUserFld;
		private RoleLookUpField roleFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			applicationUserFld = addField(ApplicationUserLookUpField.class, UserRoleProperty.USER, 0, 0);
			roleFld = addField(RoleLookUpField.class, UserRoleProperty.ROLE, 0, 1);
			activeFld = addField(ActiveSelect.class, UserRoleProperty.ACTIVE, 1, 0);

			Object parentEntity = getParentEntity();
			if (parentEntity instanceof ApplicationUser) {
				ApplicationUser applicationUser = (ApplicationUser) parentEntity;
				applicationUserFld.setValue(applicationUser);
				applicationUserFld.setReadOnly(true);
			}
			if (parentEntity instanceof Role) {
				Role role = (Role) parentEntity;
				roleFld.setValue(role);
				roleFld.setReadOnly(true);
			}
		}

		@Override
		public Object getCriteria() {
			UserRoleVO vo = new UserRoleVO();
			vo.setActive(activeFld.getItemId());
			vo.setUserId(applicationUserFld.getItemId());
			vo.setRoleId(roleFld.getItemId());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private ApplicationUserLookUpField userFld;
		private RoleLookUpField roleFld;
		private CCheckBox activeFld;

		private UserRole userRole;

		public Form() {
			super();
		}

		@Override
		public void executeBuild() {
			formGrid = createFormGrid();
			addComponent(formGrid);
		}

		private CGridLayout createFormGrid() {
			formGrid = ComponentUtil.createGrid();
			formGrid.setSpacing(true);

			userFld = formGrid.addField(ApplicationUserLookUpField.class, UserRoleProperty.USER, 0, 0);

			roleFld = formGrid.addField(RoleLookUpField.class, UserRoleProperty.ROLE, 0, 1);

			activeFld = formGrid.addField(CCheckBox.class, UserRoleProperty.ACTIVE, 2, 0);

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

}
