package software.simple.solutions.framework.core.web.view.forms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.github.appreciated.card.Card;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;

import software.simple.solutions.framework.core.components.select.GenderSelect;
import software.simple.solutions.framework.core.constants.FileReference;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.service.facade.PersonServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CPopupDateField;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.CustomCheckBox;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.person.SoundexPersonGrid;
import software.simple.solutions.framework.core.web.view.person.SoundexPersonSelected;

@Route(value = Routes.PERSON_EDIT, layout = MainView.class)
public class PersonForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private static final Logger logger = LogManager.getLogger(PersonForm.class);

	private CFormLayout formGrid;
	private Image imageField;
	private CTextField lastNameFld;
	private CTextField firstNameFld;
	private CTextField middleNameFld;
	private CPopupDateField dateOfBirthFld;
	private GenderSelect genderFld;
	private CustomCheckBox activeFld;
	private HorizontalLayout mainLayout;
	private Person person;
	private Panel personInfoCard;
	private Panel personSoundexCard;
	private SoundexPersonGrid soundexPersonGrid;
	private VerticalLayout personMainLayout;

	// @formatter:off
		private String styles = ".applayout-profile-image { "
		        + "border-radius: 50%;"
		        + "object-fit: cover;"
		        + "border: 2px solid #ffc13f;"
		        + " }"
		        ;
		// @formatter:on

	public PersonForm() {
		super();
		/*
		 * The code below register the style file dynamically. Normally you
		 * use @StyleSheet annotation for the component class. This way is
		 * chosen just to show the style file source code.
		 */
		StreamRegistration resource = UI.getCurrent().getSession().getResourceRegistry()
				.registerResource(new StreamResource("styles.css", () -> {
					byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
					return new ByteArrayInputStream(bytes);
				}));
		UI.getCurrent().getPage().addStyleSheet("base://" + resource.getResourceUri().toString());
	}

	@Override
	public void executeBuild() {
		mainLayout = new HorizontalLayout();
		mainLayout.setWidthFull();
		add(mainLayout);

		VerticalLayout navigationLayout = new VerticalLayout();
		navigationLayout.setWidth("-1px");
		mainLayout.add(navigationLayout);

		Card profileImageCard = new Card();
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setWidth("-1px");
		verticalLayout.setMargin(true);
		imageField = new Image("img/profile-pic-300px.jpg", "profile-image");
		imageField.setWidth("150px");
		imageField.setHeight("150px");
		imageField.addClassName("applayout-profile-image");
		verticalLayout.add(imageField);

		CButton removeImageBtn = new CButton();
		removeImageBtn.setWidthFull();
		removeImageBtn.setIcon(FontAwesome.Solid.TRASH.create());
		removeImageBtn.setText(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_DELETE,
				UI.getCurrent().getLocale()));
		verticalLayout.add(removeImageBtn);
		profileImageCard.add(verticalLayout);
		navigationLayout.add(profileImageCard);
		imageField.addClickListener(new ComponentEventListener<ClickEvent<Image>>() {

			private static final long serialVersionUID = -1532237862756475958L;

			@Override
			public void onComponentEvent(ClickEvent<Image> event) {
				Dialog dialog = new Dialog();
				MemoryBuffer buffer = new MemoryBuffer();
				Upload upload = new Upload(buffer);
				upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
				upload.setMaxFileSize(1000000);
				dialog.add(upload);
				dialog.open();

				upload.addSucceededListener(new ComponentEventListener<SucceededEvent>() {

					private static final long serialVersionUID = 3929593754743732077L;

					@Override
					public void onComponentEvent(SucceededEvent event) {
						StreamResource resource = new StreamResource(buffer.getFileName(),
								() -> buffer.getInputStream());
						imageField.setSrc(resource);
						try {
							byte[] byteArray = IOUtils.toByteArray(buffer.getInputStream());
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

						} catch (FrameworkException | IOException e) {
							logger.error(e.getMessage(), e);
							DetailsWindow.build(e);
						}
					}
				});
			}
		});

		personMainLayout = new VerticalLayout();
		personMainLayout.setWidthFull();
		mainLayout.add(personMainLayout);
		mainLayout.expand(personMainLayout);

		personInfoCard = createPersonLayout();
		personInfoCard.setWidthFull();
		personMainLayout.add(personInfoCard);

		personSoundexCard = createPersonSoundexLayout();
		personSoundexCard.setWidthFull();
		personMainLayout.add(personSoundexCard);
	}

	private Panel createPersonLayout() {
		personInfoCard = new Panel();
		personInfoCard.setHeaderKey(PersonProperty.PERSON_INFO);
		formGrid = new CFormLayout();
		formGrid.setMaxWidth("800px");
		personInfoCard.add(formGrid);

		firstNameFld = formGrid.add(CTextField.class, PersonProperty.FIRST_NAME);

		middleNameFld = formGrid.add(CTextField.class, PersonProperty.MIDDLE_NAME);

		lastNameFld = formGrid.add(CTextField.class, PersonProperty.LAST_NAME);

		dateOfBirthFld = formGrid.add(CPopupDateField.class, PersonProperty.DATE_OF_BIRTH);
		dateOfBirthFld.setMax(LocalDate.now());

		genderFld = formGrid.add(GenderSelect.class, GenderProperty.GENDER);

		activeFld = formGrid.add(CustomCheckBox.class, PersonProperty.ACTIVE);

		return personInfoCard;
	}

	private Panel createPersonSoundexLayout() {
		personSoundexCard = new Panel();
		personSoundexCard.setHeaderKey(PersonProperty.PERSON_SOUNDEX);

		Div content = new Div();
		content.setMaxWidth("800px");
		content.getElement().setProperty("innerHTML", PropertyResolver.getPropertyValueByLocale(
				PersonProperty.PERSON_SOUNDEX_USE_THIS_EXPLANATION, UI.getCurrent().getLocale()));
		content.getStyle().set("font-size", "var(--lumo-font-size-m)");
		content.getStyle().set("color", "var(--lumo-primary-text-color)");
		content.getStyle().set("padding", "15px");
		personSoundexCard.add(content);

		Button continueBtn = new Button();
		continueBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		continueBtn.setText(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.BUTTON_CONTINUE, UI.getCurrent().getLocale()));
		personSoundexCard.add(continueBtn);
		continueBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -8124155574758924440L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				getByPassValidationSubject().onNext(Boolean.TRUE);
			}
		});

		soundexPersonGrid = new SoundexPersonGrid();
		soundexPersonGrid.setMaxWidth("800px");
		personSoundexCard.add(soundexPersonGrid);
		personSoundexCard.setVisible(false);
		soundexPersonGrid.setSoundexPersonSelected(new SoundexPersonSelected() {

			@Override
			public void selected(Person person) {
				getPushEntityToFormSubject().onNext(person);
			}
		});

		return personSoundexCard;
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
			StreamResource resource = new StreamResource("profile-image.jpg",
					() -> new ByteArrayInputStream(entityFile.getFileObject()));
			if (resource != null) {
				imageField.setSrc(resource);
			}
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

	@Override
	public boolean validate() throws FrameworkException {
		if (person == null) {
			personSoundexCard.setVisible(false);
			PersonServiceFacade personServiceFacade = PersonServiceFacade.get(UI.getCurrent());
			List<Person> persons = personServiceFacade.listBySoundex(firstNameFld.getValue(), lastNameFld.getValue(),
					dateOfBirthFld.getValue(), genderFld.getItemId());
			if (persons != null && !persons.isEmpty()) {
				personSoundexCard.setVisible(true);
				soundexPersonGrid.build(persons);
				return false;
			}
		}
		return true;
	}

}
