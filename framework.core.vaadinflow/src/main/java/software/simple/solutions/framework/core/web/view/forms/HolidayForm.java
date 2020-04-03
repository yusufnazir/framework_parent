package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.Holiday;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.HolidayProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.HolidayVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CPopupDateField;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.HOLIDAY_EDIT, layout = MainView.class)
public class HolidayForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField nameFld;
	private CPopupDateField dateField;

	private Holiday holiday;

	public HolidayForm() {
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

		nameFld = formGrid.add(CTextField.class, HolidayProperty.NAME);
		dateField = formGrid.add(CPopupDateField.class, HolidayProperty.DATE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Holiday setFormValues(Object entity) throws FrameworkException {
		holiday = (Holiday) entity;
		nameFld.setValue(holiday.getName());
		dateField.setValue(holiday.getDate());

		return holiday;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		nameFld.setRequiredIndicatorVisible(true);
		dateField.setRequiredIndicatorVisible(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		HolidayVO vo = new HolidayVO();

		vo.setId(holiday == null ? null : holiday.getId());

		vo.setName(nameFld.getValue());
		vo.setDate(dateField.getValue());

		return vo;
	}

}
