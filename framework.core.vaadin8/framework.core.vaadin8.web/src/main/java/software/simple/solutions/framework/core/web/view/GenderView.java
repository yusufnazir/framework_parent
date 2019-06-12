package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.service.IGenderService;
import software.simple.solutions.framework.core.service.facade.GenderServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.GenderVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class GenderView extends BasicTemplate<Gender> {

	private static final long serialVersionUID = 6503015064562511801L;

	public GenderView() {
		setEntityClass(Gender.class);
		setServiceClass(GenderServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Gender::getName, GenderProperty.NAME);
		addContainerProperty(Gender::getKey, GenderProperty.PROPERTY_KEY);
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
		private CStringIntervalLayout keyFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, GenderProperty.NAME, 0, 0);
			keyFld = addField(CStringIntervalLayout.class, GenderProperty.PROPERTY_KEY, 1, 0);
		}

		@Override
		public Object getCriteria() {
			GenderVO vo = new GenderVO();
			vo.setNameInterval(nameFld.getValue());
			vo.setKeyInterval(keyFld.getValue());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField nameFld;
		private CTextArea keyFld;

		private Gender gender;

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

			nameFld = formGrid.addField(CTextField.class, GenderProperty.NAME, 0, 0);

			keyFld = formGrid.addField(CTextArea.class, GenderProperty.PROPERTY_KEY, 0, 1, 1, 1);
			keyFld.setRows(1);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Gender setFormValues(Object entity) throws FrameworkException {
			gender = (Gender) entity;
			nameFld.setValue(gender.getName());
			keyFld.setValue(gender.getKey());

			return gender;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			nameFld.setRequired();
			keyFld.setRequired();
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			GenderVO vo = new GenderVO();

			vo.setId(gender == null ? null : gender.getId());

			vo.setKey(keyFld.getValue());
			vo.setName(nameFld.getValue());

			return vo;
		}

	}

}
