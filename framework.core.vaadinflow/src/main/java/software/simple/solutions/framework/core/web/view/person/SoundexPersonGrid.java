package software.simple.solutions.framework.core.web.view.person;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.StreamResource;

import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.FileReference;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class SoundexPersonGrid extends Grid<Person> {

	private static final long serialVersionUID = 338012915910419680L;

	private UI ui;
	private SoundexPersonSelected soundexPersonSelected;

	public SoundexPersonGrid() {
		super();
		ui = UI.getCurrent();
		setHeightByRows(true);
	}

	public void build(List<Person> perons) {
		removeAllColumns();

		Column<Person> useThisColumn = addComponentColumn(new ValueProvider<Person, Button>() {

			private static final long serialVersionUID = 8952466977973632156L;

			@Override
			public Button apply(Person source) {
				Button selectBtn = new Button();
				selectBtn.setIcon(FontAwesome.Solid.CHECK_CIRCLE.create());
				selectBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

					private static final long serialVersionUID = 5571473980262840931L;

					@Override
					public void onComponentEvent(ClickEvent<Button> event) {
						useThisPerson(source);
					}
				});
				return selectBtn;
			}
		});
		useThisColumn.setHeader(
				PropertyResolver.getPropertyValueByLocale(PersonProperty.PERSON_SOUNDEX_USE_THIS, UI.getCurrent().getLocale()));

		Column<Person> imageColumn = addComponentColumn(new ValueProvider<Person, Image>() {

			private static final long serialVersionUID = -7132942008868854428L;

			@Override
			public Image apply(Person source) {
				Image imageField = new Image("img/profile-pic-300px.jpg", "profile-image");
				imageField.addClassName("applayout-profile-image");
				imageField.setHeight("75px");
				imageField.setWidth("75px");

				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							if (source != null) {
								IFileService fileService = ContextProvider.getBean(IFileService.class);
								EntityFile entityFile = fileService.findFileByEntityAndType(source.getId().toString(),
										ReferenceKey.PERSON, FileReference.USER_PROFILE_IMAGE);

								if (entityFile != null) {
									StreamResource resource = new StreamResource(entityFile.getName(),
											() -> new ByteArrayInputStream(entityFile.getFileObject()));
									if (resource != null) {
										ui.access(new Command() {

											private static final long serialVersionUID = 337925124788709903L;

											@Override
											public void execute() {
												imageField.setSrc(resource);
											}
										});
									}
								}
							}
						} catch (FrameworkException e) {
							//
						}
					}
				});
				thread.start();
				//
				return imageField;
			}
		});
		imageColumn.setHeader(
				PropertyResolver.getPropertyValueByLocale(PersonProperty.IMAGE, UI.getCurrent().getLocale()));

		Column<Person> firstNameColumn = addColumn(new ValueProvider<Person, String>() {

			private static final long serialVersionUID = -525450715574471673L;

			@Override
			public String apply(Person source) {
				return source.getFirstName();
			}
		});
		firstNameColumn.setHeader(
				PropertyResolver.getPropertyValueByLocale(PersonProperty.FIRST_NAME, UI.getCurrent().getLocale()));

		Column<Person> lastNameColumn = addColumn(new ValueProvider<Person, String>() {

			private static final long serialVersionUID = 1557417980100185006L;

			@Override
			public String apply(Person source) {
				return source.getLastName();
			}
		});
		lastNameColumn.setHeader(
				PropertyResolver.getPropertyValueByLocale(PersonProperty.LAST_NAME, UI.getCurrent().getLocale()));

		Column<Person> dateOfBirthColumn = addColumn(new ValueProvider<Person, String>() {

			private static final long serialVersionUID = -1966699192252704884L;

			@Override
			public String apply(Person source) {
				LocalDate dob = source.getDateOfBirth();
				if (dob == null) {
					return null;
				}
				String formattedDate = dob.format(Constants.DATE_FORMATTER.withLocale(UI.getCurrent().getLocale()));
				return formattedDate;
			}
		});
		dateOfBirthColumn.setHeader(
				PropertyResolver.getPropertyValueByLocale(PersonProperty.DATE_OF_BIRTH, UI.getCurrent().getLocale()));

		Column<Person> ageColumn = addColumn(new ValueProvider<Person, Long>() {

			private static final long serialVersionUID = 4071899583789743594L;

			@Override
			public Long apply(Person source) {
				return source.getAge();
			}
		});
		ageColumn.setHeader(PropertyResolver.getPropertyValueByLocale(PersonProperty.AGE, UI.getCurrent().getLocale()));

		Column<Person> genderColumn = addColumn(new ValueProvider<Person, String>() {

			private static final long serialVersionUID = 2603744171325328736L;

			@Override
			public String apply(Person source) {
				Gender gender = source.getGender();
				if (gender == null) {
					return null;
				}
				return PropertyResolver.getPropertyValueByLocale(ReferenceKey.GENDER, gender.getId().toString(),
						UI.getCurrent().getLocale(), null, gender.getName());
			}
		});
		genderColumn.setHeader(
				PropertyResolver.getPropertyValueByLocale(PersonProperty.GENDER, UI.getCurrent().getLocale()));

		if (perons != null) {
			setItems(perons.stream());
		}
	}

	public SoundexPersonSelected getSoundexPersonSelected() {
		return soundexPersonSelected;
	}

	public void setSoundexPersonSelected(SoundexPersonSelected soundexPersonSelected) {
		this.soundexPersonSelected = soundexPersonSelected;
	}

	protected void useThisPerson(Person source) {
		soundexPersonSelected.selected(source);
	}

}
