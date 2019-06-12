package software.simple.solutions.framework.core.web.view;

import java.time.LocalDate;
import java.util.List;

import com.vaadin.data.ValueProvider;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;

import software.simple.solutions.framework.core.annotations.SupportedPrivileges;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.select.GenderSelect;
import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.service.IPersonService;
import software.simple.solutions.framework.core.service.facade.PersonServiceFacade;
import software.simple.solutions.framework.core.upload.ImageField;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

@SpringView(name = "software.simple.solutions.framework.core.web.view.PersonView")
@SupportedPrivileges(extraPrivileges = { Privileges.PERSON_SHOW_GENDER, Privileges.PERSON_SHOW_DOB })
public class PersonView extends BasicTemplate<Person> {

	private static final long serialVersionUID = 6503015064562511801L;

	public PersonView() {
		setEntityClass(Person.class);
		setServiceClass(PersonServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setGridRowHeight(75);
	}

	@Override
	public void setUpCustomColumns() {
		addComponentContainerProperty(new ValueProvider<Person, Image>() {

			private static final long serialVersionUID = 1818232591747121810L;

			@Override
			public Image apply(Person source) {
				Image image = new Image();
				image.setSource(CxodeIcons.PROFILE_IMAGE);
				image.setWidth("75px");
				return image;
			}
		}, PersonProperty.IMAGE);
		addContainerProperty(Person::getLastName, PersonProperty.LAST_NAME);
		addContainerProperty(Person::getFirstName, PersonProperty.FIRST_NAME);
		addContainerProperty(new ValueProvider<Person, String>() {

			private static final long serialVersionUID = 7700757210287029477L;

			@Override
			public String apply(Person source) {
				if (source.getGender() != null) {
					if (source.getGender().getKey() != null) {
						return PropertyResolver.getPropertyValueByLocale(source.getGender().getKey());
					} else {
						return source.getGender().getName();
					}
				}
				return source.getGender() == null ? null
						: PropertyResolver.getPropertyValueByLocale(source.getGender().getKey());
			}
		}, PersonProperty.GENDER);
		addContainerProperty(Person::getAge, PersonProperty.AGE);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CTextField lastNameFld;
		private CTextField firstNameFld;
		private GenderSelect genderFld;

		@Override
		public void executeBuild() {

			firstNameFld = createField(CTextField.class, PersonProperty.FIRST_NAME, 0, 0);

			lastNameFld = createField(CTextField.class, PersonProperty.LAST_NAME, 1, 0);

			genderFld = createField(GenderSelect.class, PersonProperty.GENDER, 2, 0);
			genderFld.setVisible(false);
			if (getViewDetail().getPrivileges().contains(Privileges.PERSON_SHOW_GENDER)) {
				genderFld.setVisible(true);
			}

		}

		@Override
		public Object getCriteria() {
			PersonVO vo = new PersonVO();
			vo.setFirstName(firstNameFld.getValue());
			vo.setLastName(lastNameFld.getValue());
			vo.setGenderId(genderFld.getItemId());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private ImageField imageField;
		private CTextField lastNameFld;
		private CTextField firstNameFld;
		private CTextField middleNameFld;
		private CPopupDateField dateOfBirthFld;
		private GenderSelect genderFld;
		private CCheckBox activeFld;
		private HorizontalLayout personInfoLayout;

		private Person person;

		private List<ComboItem> genderListing;

		@Override
		public void executeBuild() {
			personInfoLayout = createPersonLayout();
			addComponent(personInfoLayout);
		}

		private HorizontalLayout createPersonLayout() {
			personInfoLayout = new HorizontalLayout();

			imageField = new ImageField();
			imageField.setImageHeight("100px");
			personInfoLayout.addComponent(imageField);

			formGrid = ComponentUtil.createGrid();
			personInfoLayout.addComponent(formGrid);

			firstNameFld = formGrid.addField(CTextField.class, PersonProperty.FIRST_NAME, 0, 0);

			middleNameFld = formGrid.addField(CTextField.class, PersonProperty.MIDDLE_NAME, 0, 1);

			lastNameFld = formGrid.addField(CTextField.class, PersonProperty.LAST_NAME, 0, 2);

			dateOfBirthFld = formGrid.addField(CPopupDateField.class, PersonProperty.DATE_OF_BIRTH, 1, 0);
			dateOfBirthFld.setRangeEnd(LocalDate.now());
			dateOfBirthFld.setVisible(false);
			if (getViewDetail().getPrivileges().contains(Privileges.PERSON_SHOW_DOB)) {
				dateOfBirthFld.setVisible(true);
			}

			genderFld = formGrid.addField(GenderSelect.class, GenderProperty.GENDER, 1, 1);
			genderFld.setVisible(false);
			if (getViewDetail().getPrivileges().contains(Privileges.PERSON_SHOW_GENDER)) {
				genderFld.setVisible(true);
			}

			activeFld = formGrid.addField(CCheckBox.class, PersonProperty.ACTIVE, 1, 2);

			formGrid.setComponentAlignment(activeFld, Alignment.BOTTOM_LEFT);

			return personInfoLayout;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Person setFormValues(Object entity) throws FrameworkException {
			person = (Person) entity;
			firstNameFld.setValue(person.getFirstName());
			middleNameFld.setValue(person.getMiddleName());
			lastNameFld.setValue(person.getLastName());
			dateOfBirthFld.setValue(person.getDateOfBirth());
			activeFld.setValue(person.getActive());

			genderFld.setValue(person.getGender() == null ? null : person.getGender().getId());

			firstNameFld.setRequired();
			lastNameFld.setRequired();
			dateOfBirthFld.setRequired();
			genderFld.setRequired();
			return person;
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			PersonVO vo = new PersonVO();

			vo.setId(person == null ? null : person.getId());
			vo.setFirstName(firstNameFld.getValue());
			vo.setLastName(lastNameFld.getValue());
			vo.setMiddleName(middleNameFld.getValue());
			vo.setDateOfBirth(dateOfBirthFld.getValue());
			vo.setGenderId(genderFld.getItemId());
			vo.setActive(activeFld.getValue());
			return vo;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
		}

	}

}
