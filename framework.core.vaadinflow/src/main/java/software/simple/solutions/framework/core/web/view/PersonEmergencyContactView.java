package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonEmergencyContact;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;
import software.simple.solutions.framework.core.service.facade.PersonEmergencyContactServiceFacade;
import software.simple.solutions.framework.core.valueobjects.PersonEmergencyContactVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.PersonEmergencyContactForm;

@Route(value = Routes.PERSON_EMERGENCY_CONTACT, layout = MainView.class)
public class PersonEmergencyContactView extends BasicTemplate<PersonEmergencyContact> {

	private static final long serialVersionUID = 6503015064562511801L;

	public PersonEmergencyContactView() {
		setEntityClass(PersonEmergencyContact.class);
		setServiceClass(PersonEmergencyContactServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(PersonEmergencyContactForm.class);
		setParentReferenceKey(ReferenceKey.PERSON_EMERGENCY_CONTACT);
		setEditRoute(Routes.PERSON_EMERGENCY_CONTACT_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(PersonEmergencyContact::getPerson, PersonEmergencyContactProperty.PERSON);
		addContainerProperty(PersonEmergencyContact::getName, PersonEmergencyContactProperty.NAME);
		addContainerProperty(new ValueProvider<PersonEmergencyContact, String>() {

			private static final long serialVersionUID = -8883000096337137114L;

			@Override
			public String apply(PersonEmergencyContact personEmergencyContact) {
				RelationType relationType = personEmergencyContact.getRelationType();
				if (relationType != null) {
					return relationType.getCaption();
				} else {
					return null;
				}
			}
		}, PersonEmergencyContactProperty.RELATION_TYPE);
		addContainerProperty(PersonEmergencyContact::getContactNumber, PersonEmergencyContactProperty.CONTACT_NUMBER);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout firstNameFld;
		private CStringIntervalLayout lastNameFld;
		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout relationshipFld;
		private CStringIntervalLayout contactNumberFld;

		@Override
		public void executeBuild() {

			firstNameFld = addField(CStringIntervalLayout.class, PersonEmergencyContactProperty.PERSON_FIRST_NAME, 0,
					0);

			lastNameFld = addField(CStringIntervalLayout.class, PersonEmergencyContactProperty.PERSON_LAST_NAME, 0, 1);

			nameFld = addField(CStringIntervalLayout.class, PersonEmergencyContactProperty.NAME, 1, 0);

			relationshipFld = addField(CStringIntervalLayout.class, PersonEmergencyContactProperty.RELATION_TYPE, 1, 1);

			contactNumberFld = addField(CStringIntervalLayout.class, PersonEmergencyContactProperty.CONTACT_NUMBER, 2,
					0);
			
			Person person = getIfParentEntity(Person.class);
			if (person != null) {
				firstNameFld.setValue(person.getFirstName());
				firstNameFld.setReadOnly(true);

				lastNameFld.setValue(person.getLastName());
				lastNameFld.setReadOnly(true);
			}

		}

		@Override
		public Object getCriteria() {
			PersonEmergencyContactVO vo = new PersonEmergencyContactVO();
			vo.setPersonFirstNameInterval(firstNameFld.getValue());
			vo.setPersonLastNameInterval(lastNameFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setRelationshipInterval(relationshipFld.getValue());
			vo.setContactNumberInterval(contactNumberFld.getValue());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
