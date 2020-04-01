package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.service.facade.PersonInformationServiceFacade;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.PersonInformationForm;

@Route(value = Routes.PERSON_INFORMATION, layout = MainView.class)
public class PersonInformationView extends BasicTemplate<PersonInformation> {

	private static final long serialVersionUID = 6503015064562511801L;

	public PersonInformationView() {
		setEntityClass(PersonInformation.class);
		setServiceClass(PersonInformationServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(PersonInformationForm.class);
		setParentReferenceKey(ReferenceKey.PERSON_INFORMATION);
		setEditRoute(Routes.PERSON_INFORMATION_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(PersonInformation::getPerson, PersonInformationProperty.PERSON);
		addContainerProperty(PersonInformation::getPrimaryEmail, PersonInformationProperty.PRIMARY_EMAIL);
		addContainerProperty(PersonInformation::getPrimaryContactNumber,
				PersonInformationProperty.PRIMARY_CONTACT_NUMBER);
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
		private CStringIntervalLayout emailFld;
		private CStringIntervalLayout contactNumberFld;

		@Override
		public void executeBuild() {

			firstNameFld = addField(CStringIntervalLayout.class, PersonInformationProperty.PERSON_FIRST_NAME, 0, 0);

			lastNameFld = addField(CStringIntervalLayout.class, PersonInformationProperty.PERSON_LAST_NAME, 0, 1);

			emailFld = addField(CStringIntervalLayout.class, PersonInformationProperty.EMAIL, 1, 0);

			contactNumberFld = addField(CStringIntervalLayout.class, PersonInformationProperty.CONTACT_NUMBER, 1, 1);

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
			PersonInformationVO vo = new PersonInformationVO();
			vo.setPersonFirstNameInterval(firstNameFld.getValue());
			vo.setPersonLastNameInterval(lastNameFld.getValue());
			vo.setEmailInterval(emailFld.getValue());
			vo.setContactNumberInterval(contactNumberFld.getValue());
			Person person = getIfParentEntity(Person.class);
			if (person != null) {
				vo.setPersonId(person.getId());
			}

			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
