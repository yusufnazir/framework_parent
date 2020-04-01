package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.GenderVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.GENDER_EDIT, layout = MainView.class)
public class GenderForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField nameFld;

	private Gender gender;

	public GenderForm() {
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
		formCard.setMinWidth("600px");

		nameFld = formGrid.add(CTextField.class, GenderProperty.NAME);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Gender setFormValues(Object entity) throws FrameworkException {
		gender = (Gender) entity;
		nameFld.setValue(gender.getName());

		return gender;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		nameFld.setRequiredIndicatorVisible(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		GenderVO vo = new GenderVO();

		vo.setId(gender == null ? null : gender.getId());

		vo.setName(nameFld.getValue());

		return vo;
	}

}
