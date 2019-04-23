package software.simple.solutions.framework.core.web.view;

import com.vaadin.data.Binder;
import com.vaadin.data.validator.EmailValidator;

import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.web.FormBasicTemplate;

public class PersonInformationFormView extends FormBasicTemplate {

	private static final long serialVersionUID = -9131590077272243240L;

	public PersonInformationFormView() {
		setServiceClass(IPersonInformationService.class);
		setFormClass(Form.class);
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField primaryEmailFld;
		private CTextField secondaryEmailFld;
		private CTextField primaryContactNumberFld;
		private CTextField secondaryContactNumberFld;

		private PersonInformation personInformation;

		private Person person;

		@Override
		public void executeBuild() {
			formGrid = ComponentUtil.createGrid();
			addComponent(formGrid);

			primaryEmailFld = formGrid.addField(CTextField.class, PersonInformationProperty.PRIMARY_EMAIL, 0, 0);
			new Binder<String>().forField(primaryEmailFld).withValidator(new EmailValidator("Incorrect format"));

			secondaryEmailFld = formGrid.addField(CTextField.class, PersonInformationProperty.SECONDARY_EMAIL, 0, 1);
			primaryContactNumberFld = formGrid.addField(CTextField.class,
					PersonInformationProperty.PRIMARY_CONTACT_NUMBER, 1, 0);
			secondaryContactNumberFld = formGrid.addField(CTextField.class,
					PersonInformationProperty.SECONDARY_CONTACT_NUMBER, 1, 1);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
		}

		@SuppressWarnings("unchecked")
		@Override
		public PersonInformation setFormValues(Object entity) throws FrameworkException {
			IPersonInformationService personInformationService = ContextProvider
					.getBean(IPersonInformationService.class);
			person = getParentEntity();
			personInformation = personInformationService.getByPerson(person.getId());
			if (personInformation != null) {
				primaryEmailFld.setValue(personInformation.getPrimaryEmail());
				secondaryEmailFld.setValue(personInformation.getSecondaryEmail());
				primaryContactNumberFld.setValue(personInformation.getPrimaryContactNumber());
				secondaryContactNumberFld.setValue(personInformation.getSecondaryContactNumber());
			}
			return personInformation;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			PersonInformationVO vo = new PersonInformationVO();

			vo.setId(personInformation == null ? null : personInformation.getId());

			vo.setPersonId(person.getId());
			vo.setPrimaryEmail(primaryEmailFld.getValue());
			vo.setSecondaryEmail(secondaryEmailFld.getValue());
			vo.setPrimaryContactNumber(primaryContactNumberFld.getValue());
			vo.setSecondaryContactNumber(secondaryContactNumberFld.getValue());

			return vo;
		}
	}

}
