package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.Country;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CountryProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.CountryVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.COUNTRY_EDIT, layout = MainView.class)
public class CountryForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField nameFld;
	private CTextField alpha2Fld;
	private CTextField alpha3Fld;
	private CCheckBox activeFld;

	private Country country;

	public CountryForm() {
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

		nameFld = formGrid.add(CTextField.class, CountryProperty.NAME);
		alpha2Fld = formGrid.add(CTextField.class, CountryProperty.ALPHA2);
		alpha3Fld = formGrid.add(CTextField.class, CountryProperty.ALPHA3);
		activeFld = formGrid.add(CCheckBox.class, CountryProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Country setFormValues(Object entity) throws FrameworkException {
		country = (Country) entity;
		nameFld.setValue(country.getName());
		alpha2Fld.setValue(country.getAlpha2());
		alpha3Fld.setValue(country.getAlpha3());
		activeFld.setValue(country.getActive());

		return country;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		nameFld.setRequiredIndicatorVisible(true);
		alpha2Fld.setRequiredIndicatorVisible(true);
		alpha3Fld.setRequiredIndicatorVisible(true);
		activeFld.setValue(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		CountryVO vo = new CountryVO();

		vo.setId(country == null ? null : country.getId());
		vo.setName(nameFld.getValue());
		vo.setAlpha2(alpha2Fld.getValue());
		vo.setAlpha3(alpha3Fld.getValue());
		vo.setActive(activeFld.getValue());

		return vo;
	}

}
