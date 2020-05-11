package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.select.RelationTypeSelect;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonEmergencyContact;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.PersonEmergencyContactVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.lookup.PersonLookUpField;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.PERSON_EMERGENCY_CONTACT_EDIT, layout = MainView.class)
public class PersonEmergencyContactForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private PersonLookUpField personLookUpField;
	private CTextField contactNumberFld;
	private CTextField nameFld;
	private RelationTypeSelect relationTypeFld;
	private CCheckBox activeFld;

	private PersonEmergencyContact personEmergencyContact;

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

		personLookUpField = new PersonLookUpField();
		personLookUpField.setCaptionByKey(PersonEmergencyContactProperty.PERSON);
		
		HorizontalLayout horizontalLayout = formGrid.add(HorizontalLayout.class, PersonEmergencyContactProperty.PERSON);
		horizontalLayout.add(personLookUpField);
		horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.END);
		horizontalLayout.setVerticalComponentAlignment(Alignment.END, personLookUpField);
		personLookUpField.setRequiredIndicatorVisible(true);
		nameFld = formGrid.add(CTextField.class, PersonEmergencyContactProperty.NAME);
		nameFld.setRequiredIndicatorVisible(true);
		relationTypeFld = formGrid.add(RelationTypeSelect.class,
				PersonEmergencyContactProperty.RELATION_TYPE);
		relationTypeFld.setRequiredIndicatorVisible(true);
		contactNumberFld = formGrid.add(CTextField.class, PersonEmergencyContactProperty.CONTACT_NUMBER);
		contactNumberFld.setRequiredIndicatorVisible(true);

		activeFld = formGrid.add(CCheckBox.class, PersonEmergencyContactProperty.ACTIVE);

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
	public PersonEmergencyContact setFormValues(Object entity) throws FrameworkException {
		personEmergencyContact = (PersonEmergencyContact) entity;
		personLookUpField.setValue(personEmergencyContact.getPerson());
		contactNumberFld.setValue(personEmergencyContact.getContactNumber());
		nameFld.setValue(personEmergencyContact.getName());
		relationTypeFld.setValue(personEmergencyContact.getRelationType());
		activeFld.setValue(personEmergencyContact.getActive());
		
		Person person = getIfParentEntity(Person.class);
		if (person != null) {
			personLookUpField.disableForParent();
		}
		
		return personEmergencyContact;
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		PersonEmergencyContactVO vo = new PersonEmergencyContactVO();

		vo.setId(personEmergencyContact == null ? null : personEmergencyContact.getId());
		vo.setPersonId(personLookUpField.getItemId());
		vo.setName(nameFld.getValue());
		vo.setContactNumber(contactNumberFld.getValue());
		vo.setRelationTypeId(relationTypeFld.getItemId());
		vo.setActive(activeFld.getValue());
		return vo;
	}

}
