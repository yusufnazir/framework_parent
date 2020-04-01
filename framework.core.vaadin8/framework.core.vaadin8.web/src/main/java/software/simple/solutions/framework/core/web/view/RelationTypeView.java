package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.service.facade.RelationTypeServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.RelationTypeVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class RelationTypeView extends BasicTemplate<Role> {

	private static final long serialVersionUID = 6503015064562511801L;

	public RelationTypeView() {
		setEntityClass(RelationType.class);
		setServiceClass(RelationTypeServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Role::getCode, RoleProperty.CODE);
		addContainerProperty(Role::getName, RoleProperty.NAME);
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

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, RoleProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, RoleProperty.NAME, 0, 1);
		}

		@Override
		public Object getCriteria() {
			RelationTypeVO vo = new RelationTypeVO();
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField codeFld;
		private CTextField nameFld;

		private RelationType relationType;

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

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public RelationType setFormValues(Object entity) throws FrameworkException {
			relationType = (RelationType) entity;
			codeFld.setValue(relationType.getCode());
			nameFld.setValue(relationType.getName());

			return relationType;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			nameFld.setRequired();
			codeFld.setRequired();
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			RelationTypeVO vo = new RelationTypeVO();

			vo.setId(relationType == null ? null : relationType.getId());

			vo.setCode(codeFld.getValue());
			vo.setName(nameFld.getValue());
			return vo;
		}
	}

}
