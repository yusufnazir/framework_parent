package software.simple.solutions.framework.core.web.view;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.lookup.PersonLookUpField;

public class PersonInformationView extends BasicTemplate<PersonInformation> {

	private static final long serialVersionUID = 6503015064562511801L;

	public PersonInformationView() {
		setEntityClass(PersonInformation.class);
		setServiceClass(IPersonInformationService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
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

		}

		@Override
		public Object getCriteria() {
			PersonInformationVO vo = new PersonInformationVO();
			vo.setPersonFirstNameInterval(firstNameFld.getValue());
			vo.setPersonLastNameInterval(lastNameFld.getValue());
			vo.setEmailInterval(emailFld.getValue());
			vo.setContactNumberInterval(contactNumberFld.getValue());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private PersonLookUpField personLookUpField;
		private CTextField primaryEmailFld;
		private CTextField secondaryEmailFld;
		private CTextField primaryContactNumberFld;
		private CTextField secondaryContactNumberFld;
		private CCheckBox activeFld;

		private PersonInformation personInformation;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			personLookUpField = formGrid.addField(PersonLookUpField.class, PersonInformationProperty.PERSON, 0, 0);

			primaryEmailFld = formGrid.addField(CTextField.class, PersonInformationProperty.PRIMARY_EMAIL, 0, 1);

			secondaryEmailFld = formGrid.addField(CTextField.class, PersonInformationProperty.SECONDARY_EMAIL, 0, 2);

			activeFld = formGrid.addField(CCheckBox.class, PersonInformationProperty.ACTIVE, 1, 0);

			primaryContactNumberFld = formGrid.addField(CTextField.class,
					PersonInformationProperty.PRIMARY_CONTACT_NUMBER, 1, 1);

			secondaryContactNumberFld = formGrid.addField(CTextField.class,
					PersonInformationProperty.SECONDARY_CONTACT_NUMBER, 1, 2);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
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
			activeFld.setValue(personInformation.getActive());
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
			vo.setActive(activeFld.getValue());
			return vo;
		}

	}

}
