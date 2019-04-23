package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.components.select.RoleCategorySelect;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleCategoryProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.service.IRoleService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.RoleVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class RoleView extends BasicTemplate<Role> {

	private static final long serialVersionUID = 6503015064562511801L;

	public RoleView() {
		setEntityClass(Role.class);
		setServiceClass(IRoleService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Role::getCode, RoleProperty.CODE);
		addContainerProperty(Role::getName, RoleProperty.NAME);
		addContainerProperty(Role::getDescription, RoleProperty.DESCRIPTION);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout descriptionFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, RoleProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, RoleProperty.NAME, 0, 1);
			descriptionFld = addField(CStringIntervalLayout.class, RoleProperty.DESCRIPTION, 1, 0);
			activeFld = addField(ActiveSelect.class, RoleProperty.ACTIVE, 1, 1);
		}

		@Override
		public Object getCriteria() {
			RoleVO vo = new RoleVO();
			vo.setActive(activeFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setDescriptionInterval(descriptionFld.getValue());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField codeFld;
		private CTextField nameFld;
		private CTextArea descriptionFld;
		private CCheckBox activeFld;
		private RoleCategorySelect roleCategoryFld;

		private Role role;

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

			codeFld = formGrid.addField(CTextField.class, RoleProperty.CODE, 0, 0);

			nameFld = formGrid.addField(CTextField.class, RoleProperty.NAME, 0, 1);

			roleCategoryFld = formGrid.addField(RoleCategorySelect.class, RoleCategoryProperty.ROLE_CATEGORY, 0, 2);

			descriptionFld = formGrid.addField(CTextArea.class, RoleProperty.DESCRIPTION, 1, 0, 1, 1);
			descriptionFld.setRows(2);

			activeFld = formGrid.addField(CCheckBox.class, RoleProperty.ACTIVE, 2, 0);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Role setFormValues(Object entity) throws FrameworkException {
			role = (Role) entity;
			activeFld.setValue(role.getActive());
			codeFld.setValue(role.getCode());
			nameFld.setValue(role.getName());
			descriptionFld.setValue(role.getDescription());
			roleCategoryFld.setValue(role.getRoleCategory() == null ? null : role.getRoleCategory().getId());

			return role;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			nameFld.setRequired();
			codeFld.setRequired();
			activeFld.setValue(true);
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			RoleVO vo = new RoleVO();

			vo.setId(role == null ? null : role.getId());

			vo.setActive(activeFld.getValue());
			vo.setCode(codeFld.getValue());
			vo.setName(nameFld.getValue());
			vo.setDescription(descriptionFld.getValue());
			vo.setRoleCategoryId(roleCategoryFld.getLongValue());
			return vo;
		}

	}

}
