package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.Nationality;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.NationalityProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.NationalityVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.NATIONALITY_EDIT, layout = MainView.class)
public class NationalityForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField nameFld;
	private CCheckBox activeFld;

	private Nationality nationality;

	public NationalityForm() {
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

		nameFld = formGrid.add(CTextField.class, NationalityProperty.NAME);
		activeFld = formGrid.add(CCheckBox.class, NationalityProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Nationality setFormValues(Object entity) throws FrameworkException {
		nationality = (Nationality) entity;
		nameFld.setValue(nationality.getName());
		activeFld.setValue(nationality.getActive());

		return nationality;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		nameFld.setRequiredIndicatorVisible(true);
		activeFld.setValue(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		NationalityVO vo = new NationalityVO();

		vo.setId(nationality == null ? null : nationality.getId());
		vo.setName(nameFld.getValue());
		vo.setActive(activeFld.getValue());

		return vo;
	}

}
