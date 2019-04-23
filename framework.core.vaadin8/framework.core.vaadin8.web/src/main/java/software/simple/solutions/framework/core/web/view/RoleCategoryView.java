package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.entities.RoleCategory;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleCategoryProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.service.IRoleCategoryService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.RoleCategoryVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class RoleCategoryView extends BasicTemplate<RoleCategory> {

	private static final long serialVersionUID = 6503015064562511801L;

	public RoleCategoryView() {
		setEntityClass(RoleCategory.class);
		setServiceClass(IRoleCategoryService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(RoleCategory::getName, RoleProperty.NAME);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout nameFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, RoleCategoryProperty.NAME, 0, 0);
		}

		@Override
		public Object getCriteria() {
			RoleCategoryVO vo = new RoleCategoryVO();
			vo.setNameInterval(nameFld.getValue());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField nameFld;

		private RoleCategory roleCategory;

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

			nameFld = formGrid.addField(CTextField.class, RoleProperty.NAME, 0, 0);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public RoleCategory setFormValues(Object entity) throws FrameworkException {
			roleCategory = (RoleCategory) entity;
			nameFld.setValue(roleCategory.getName());

			return roleCategory;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			nameFld.setRequired();
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			RoleCategoryVO vo = new RoleCategoryVO();

			vo.setId(roleCategory == null ? null : roleCategory.getId());

			vo.setName(nameFld.getValue());

			return vo;
		}

	}

}
