package software.simple.solutions.framework.core.web.view.forms;

import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.properties.ViewProperty;
import software.simple.solutions.framework.core.valueobjects.ViewVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;

public class ViewForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField codeFld;
	private CTextField nameFld;
	private CTextArea classNameFld;
	private CCheckBox activeFld;

	private View view;

	public ViewForm() {
		super();
	}

	@Override
	public void executeBuild() {
		createFormGrid();
	}

	private CFormLayout createFormGrid() {
		Panel formCard = new Panel();
		formCard.setHeaderKey(SystemProperty.SYSTEM_PANEL_BASIC_INFORMATION);
		add(formCard);
		formGrid = new CFormLayout();
		formCard.add(formGrid);
		formCard.setMaxWidth("400px");

		codeFld = formGrid.add(CTextField.class, ViewProperty.CODE);
		codeFld.setMaxWidth("400px");

		nameFld = formGrid.add(CTextField.class, ViewProperty.NAME);
		nameFld.setMaxWidth("400px");

		classNameFld = formGrid.add(CTextArea.class, ViewProperty.CLASS_NAME);
		classNameFld.setWidth("400px");

		activeFld = formGrid.add(CCheckBox.class, ViewProperty.ACTIVE);

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
		codeFld.setRequiredIndicatorVisible(true);
		nameFld.setRequiredIndicatorVisible(true);
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
