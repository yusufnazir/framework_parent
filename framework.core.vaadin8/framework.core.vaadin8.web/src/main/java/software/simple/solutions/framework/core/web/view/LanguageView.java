package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.LanguageProperty;
import software.simple.solutions.framework.core.service.ILanguageService;
import software.simple.solutions.framework.core.service.facade.LanguageServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.LanguageVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class LanguageView extends BasicTemplate<Language> {

	private static final long serialVersionUID = 6503015064562511801L;

	public LanguageView() {
		setEntityClass(Language.class);
		setServiceClass(LanguageServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Language::getCode, LanguageProperty.CODE);
		addContainerProperty(Language::getName, LanguageProperty.NAME);
		addContainerProperty(Language::getDescription, LanguageProperty.DESCRIPTION);
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
			codeFld = addField(CStringIntervalLayout.class, LanguageProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, LanguageProperty.NAME, 0, 1);
			descriptionFld = addField(CStringIntervalLayout.class, LanguageProperty.DESCRIPTION, 1, 0);
			activeFld = addField(ActiveSelect.class, LanguageProperty.ACTIVE, 1, 1);
		}

		@Override
		public Object getCriteria() {
			LanguageVO vo = new LanguageVO();
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

		private Language language;

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

			codeFld = formGrid.addField(CTextField.class, LanguageProperty.CODE, 0, 0);

			nameFld = formGrid.addField(CTextField.class, LanguageProperty.NAME, 0, 1);

			descriptionFld = formGrid.addField(CTextArea.class, LanguageProperty.DESCRIPTION, 1, 0, 1, 1);
			descriptionFld.setRows(2);

			activeFld = formGrid.addField(CCheckBox.class, LanguageProperty.ACTIVE, 2, 0);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Language setFormValues(Object entity) throws FrameworkException {
			language = (Language) entity;
			activeFld.setValue(language.getActive());
			codeFld.setValue(language.getCode());
			nameFld.setValue(language.getName());
			descriptionFld.setValue(language.getDescription());

			return language;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			nameFld.setRequired();
			codeFld.setRequired();
			activeFld.setValue(true);
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			LanguageVO vo = new LanguageVO();

			vo.setId(language == null ? null : language.getId());

			vo.setActive(activeFld.getValue());
			vo.setCode(codeFld.getValue());
			vo.setName(nameFld.getValue());
			vo.setDescription(descriptionFld.getValue());

			return vo;
		}

	}

}
