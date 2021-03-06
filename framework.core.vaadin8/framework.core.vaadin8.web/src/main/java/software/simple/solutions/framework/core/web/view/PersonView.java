package software.simple.solutions.framework.core.web.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.annotations.SupportedPrivileges;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.select.GenderSelect;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.FileReference;
import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.service.facade.PersonServiceFacade;
import software.simple.solutions.framework.core.upload.ImageField;
import software.simple.solutions.framework.core.upload.ImageField.UploadImage;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

@SpringView(name = "software.simple.solutions.framework.core.web.view.PersonView")
@SupportedPrivileges(extraPrivileges = { Privileges.PERSON_SHOW_GENDER, Privileges.PERSON_SHOW_DOB })
public class PersonView extends BasicTemplate<Person> {

	private static final long serialVersionUID = 6503015064562511801L;

	private static final Logger logger = LogManager.getLogger(PersonView.class);

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
				image.setHeight("75px");
				image.setWidth("75px");
				image.addStyleName("appbar-profile-image");

				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							image.setSource(CxodeIcons.PROFILE_IMAGE);
							IFileService fileService = ContextProvider.getBean(IFileService.class);
							EntityFile entityFile = fileService.findFileByEntityAndType(source.getId().toString(),
									ReferenceKey.PERSON, FileReference.USER_PROFILE_IMAGE);

							if (entityFile != null) {
								image.setSource(new StreamResource(new StreamSource() {

									private static final long serialVersionUID = -9150451917237177393L;

									@Override
									public InputStream getStream() {
										if (entityFile == null || entityFile.getFileObject() == null) {
											return null;
										}
										return new ByteArrayInputStream(entityFile.getFileObject());
									}
								}, entityFile.getName()));
							}
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
						}
					}
				});
				thread.start();

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
					return PropertyResolver.getPropertyValueByLocale(ReferenceKey.GENDER, source.getGender().getId(),
							UI.getCurrent().getLocale(), source.getGender().getName());
				}
				return null;
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

		private List<ComboItem> genderListing;
		private Person person;

		@Override
		public void executeBuild() {
			personInfoLayout = createPersonLayout();
			addComponent(personInfoLayout);
		}

		private HorizontalLayout createPersonLayout() {
			personInfoLayout = new HorizontalLayout();

			imageField = new ImageField();
			imageField.setImageHeight("100px");
			imageField.setImageWidth("100px");
			personInfoLayout.addComponent(imageField);
			imageField.setUploadHandler(new UploadImage() {

				@Override
				public void upload(InputStream inputStream) {
					try {
						byte[] byteArray = IOUtils.toByteArray(inputStream);
						IFileService fileService = ContextProvider.getBean(IFileService.class);
						EntityFileVO entityFileVO = new EntityFileVO();
						entityFileVO.setActive(true);
						entityFileVO.setDatabase(true);
						entityFileVO.setFilename(person.getCode() + ".png");
						entityFileVO.setEntityName(ReferenceKey.PERSON);
						entityFileVO.setEntityId(person.getId().toString());
						entityFileVO.setTypeOfFile(FileReference.USER_PROFILE_IMAGE);
						entityFileVO.setFileObject(byteArray);
						fileService.upLoadFile(entityFileVO);
					} catch (IOException | FrameworkException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			formGrid = ComponentUtil.createGrid();
			personInfoLayout.addComponent(formGrid);

			firstNameFld = formGrid.addField(CTextField.class, PersonProperty.FIRST_NAME, 0, 0);

			middleNameFld = formGrid.addField(CTextField.class, PersonProperty.MIDDLE_NAME, 0, 1);

			lastNameFld = formGrid.addField(CTextField.class, PersonProperty.LAST_NAME, 0, 2);

			dateOfBirthFld = formGrid.addField(CPopupDateField.class, PersonProperty.DATE_OF_BIRTH, 1, 0);
			dateOfBirthFld.setRangeEnd(LocalDate.now());
			dateOfBirthFld.setDateFormat(Constants.SIMPLE_DATE_FORMAT.toPattern());
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
			addToReferenceKey(ReferenceKey.PERSON, person);

			firstNameFld.setValue(person.getFirstName());
			middleNameFld.setValue(person.getMiddleName());
			lastNameFld.setValue(person.getLastName());
			dateOfBirthFld.setValue(person.getDateOfBirth());
			activeFld.setValue(person.getActive());

			genderFld.setValue(person.getGender() == null ? null : person.getGender().getId());

			firstNameFld.setRequiredIndicatorVisible(true);
			lastNameFld.setRequiredIndicatorVisible(true);
			dateOfBirthFld.setRequiredIndicatorVisible(true);
			genderFld.setRequiredIndicatorVisible(true);

			IFileService fileService = ContextProvider.getBean(IFileService.class);
			EntityFile entityFile = fileService.findFileByEntityAndType(person.getId().toString(), ReferenceKey.PERSON,
					FileReference.USER_PROFILE_IMAGE);
			if (entityFile != null) {
				imageField.setSource(new StreamResource(new StreamSource() {

					private static final long serialVersionUID = -1143826625995236036L;

					@Override
					public InputStream getStream() {
						if (entityFile == null || entityFile.getFileObject() == null) {
							return null;
						}
						return new ByteArrayInputStream(entityFile.getFileObject());
					}
				}, entityFile.getName()));
			}

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
			firstNameFld.setRequiredIndicatorVisible(true);
			lastNameFld.setRequiredIndicatorVisible(true);
			dateOfBirthFld.setRequiredIndicatorVisible(true);
			genderFld.setRequiredIndicatorVisible(true);
		}

	}

}
