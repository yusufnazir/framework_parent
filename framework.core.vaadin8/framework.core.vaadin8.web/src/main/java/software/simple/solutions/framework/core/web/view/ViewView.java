package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ViewProperty;
import software.simple.solutions.framework.core.service.IViewService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.ViewVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class ViewView extends BasicTemplate<View> {

	private static final long serialVersionUID = 6503015064562511801L;

	public ViewView() {
		setEntityClass(View.class);
		setServiceClass(IViewService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(View::getCode, ViewProperty.CODE);
		addContainerProperty(View::getName, ViewProperty.NAME);
		addContainerProperty(View::getViewClassName, ViewProperty.CLASS_NAME);
	}

	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout classNameFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, ViewProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, ViewProperty.NAME, 0, 1);
			classNameFld = addField(CStringIntervalLayout.class, ViewProperty.CLASS_NAME, 1, 0);
			activeFld = addField(ActiveSelect.class, ViewProperty.ACTIVE, 1, 1);
		}

		@Override
		public Object getCriteria() {
			ViewVO vo = new ViewVO();
			vo.setActive(activeFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setViewClassNameInterval(classNameFld.getValue());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField codeFld;
		private CTextField nameFld;
		private CTextArea classNameFld;
		private CCheckBox activeFld;

		private View view;

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

			codeFld = formGrid.addField(CTextField.class, ViewProperty.CODE, 0, 0);

			nameFld = formGrid.addField(CTextField.class, ViewProperty.NAME, 0, 1);

			classNameFld = formGrid.addField(CTextArea.class, ViewProperty.CLASS_NAME, 1, 0, 1, 1);
			classNameFld.setWidth("400px");

			activeFld = formGrid.addField(CCheckBox.class, ViewProperty.ACTIVE, 0, 2);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public View setFormValues(Object entity) throws FrameworkException {
			view = (View) entity;
			codeFld.setValue(view.getCode());
			nameFld.setValue(view.getName());
			classNameFld.setValue(view.getViewClassName());
			activeFld.setValue(view.getActive());

			return view;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			codeFld.setRequired();
			nameFld.setRequired();
			classNameFld.setRequired();
			activeFld.setValue(true);
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			ViewVO vo = new ViewVO();

			vo.setId(view == null ? null : view.getId());
			vo.setCode(codeFld.getValue());
			vo.setName(nameFld.getValue());
			vo.setViewClassName(classNameFld.getValue());
			vo.setActive(activeFld.getValue());

			return vo;
		}
	}
}
