package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.select.CountrySelect;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CEmailField;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.lookup.PersonLookUpField;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.PERSON_INFORMATION_EDIT, layout = MainView.class)
public class PersonInformationForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private PersonLookUpField personLookUpField;
	private CEmailField primaryEmailFld;
	private CEmailField secondaryEmailFld;
	private CTextField primaryContactNumberFld;
	private CTextField secondaryContactNumberFld;
	private CTextField streetAddressFld;
	private CTextField cityFld;
	private CTextField stateFld;
	private CTextField postalCodeFld;
	private CountrySelect countryFld;
	private CCheckBox activeFld;

	private PersonInformation personInformation;

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
		formCard.setMaxWidth("800px");

		personLookUpField = formGrid.add(PersonLookUpField.class, PersonInformationProperty.PERSON);

		primaryEmailFld = formGrid.add(CEmailField.class, PersonInformationProperty.PRIMARY_EMAIL);
		secondaryEmailFld = formGrid.add(CEmailField.class, PersonInformationProperty.SECONDARY_EMAIL);
		primaryContactNumberFld = formGrid.add(CTextField.class, PersonInformationProperty.PRIMARY_CONTACT_NUMBER);
		secondaryContactNumberFld = formGrid.add(CTextField.class, PersonInformationProperty.SECONDARY_CONTACT_NUMBER);

		streetAddressFld = formGrid.add(CTextField.class, PersonInformationProperty.STREET_ADDRESS);
		cityFld = formGrid.add(CTextField.class, PersonInformationProperty.CITY);
		stateFld = formGrid.add(CTextField.class, PersonInformationProperty.STATE);
		postalCodeFld = formGrid.add(CTextField.class, PersonInformationProperty.POSTAL_CODE);
		countryFld = formGrid.add(CountrySelect.class, PersonInformationProperty.COUNTRY);
		activeFld = formGrid.add(CCheckBox.class, PersonInformationProperty.ACTIVE);

		return formGrid;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		activeFld.setValue(true);
		Person person = getIfParentEntity(Person.class);
		if (person != null) {
			personLookUpField.setValue(person);
			personLookUpField.disableForParent();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PersonInformation setFormValues(Object entity) throws FrameworkException {
		personInformation = (PersonInformation) entity;
		personLookUpField.setValue(personInformation.getPerson());
		primaryEmailFld.setValue(personInformation.getPrimaryEmail());
		secondaryEmailFld.setValue(personInformation.getSecondaryEmail());
		primaryContactNumberFld.setValue(personInformation.getPrimaryContactNumber());
		secondaryContactNumberFld.setValue(personInformation.getSecondaryContactNumber());
		streetAddressFld.setValue(personInformation.getStreetAddress());
		cityFld.setValue(personInformation.getCity());
		stateFld.setValue(personInformation.getState());
		postalCodeFld.setValue(personInformation.getPostalCode());
		countryFld.setValue(personInformation.getCountry());
		activeFld.setValue(personInformation.getActive());

		Person person = getIfParentEntity(Person.class);
		if (person != null) {
			personLookUpField.disableForParent();
		}
		return personInformation;
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		PersonInformationVO vo = new PersonInformationVO();

		vo.setId(personInformation == null ? null : personInformation.getId());
		vo.setPersonId(personLookUpField.getItemId());
		vo.setPrimaryEmail(primaryEmailFld.getValue());
		vo.setSecondaryEmail(secondaryEmailFld.getValue());
		vo.setPrimaryContactNumber(primaryContactNumberFld.getValue());
		vo.setSecondaryContactNumber(secondaryContactNumberFld.getValue());
		vo.setStreetAddress(streetAddressFld.getValue());
		vo.setCity(cityFld.getValue());
		vo.setState(stateFld.getValue());
		vo.setActive(activeFld.getValue());
		vo.setPostalCode(postalCodeFld.getValue());
		vo.setCountryId(countryFld.getLongValue());
		return vo;
	}

}
