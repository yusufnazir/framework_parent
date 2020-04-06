package software.simple.solutions.framework.core.web.view;

import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.StreamResource;

import software.simple.solutions.framework.core.annotations.SupportedPrivileges;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.GenderSelect;
import software.simple.solutions.framework.core.constants.FileReference;
import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.service.facade.PersonServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.PersonForm;

@Route(value = Routes.PERSON, layout = MainView.class)
@SupportedPrivileges(extraPrivileges = { Privileges.PERSON_SHOW_GENDER, Privileges.PERSON_SHOW_DOB })
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class PersonView extends BasicTemplate<Person> {

	private static final long serialVersionUID = 6503015064562511801L;

	private static final Logger logger = LogManager.getLogger(PersonView.class);

	private UI ui;

	// @formatter:off
	private String styles = ".applayout-profile-image { "
	        + "border-radius: 50%;"
	        + "object-fit: cover;"
	        + "border: 2px solid #ffc13f;"
	        + " }"
	        ;
	// @formatter:on

	public PersonView() {
		setEntityClass(Person.class);
		setServiceClass(PersonServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(PersonForm.class);
		// setGridRowHeight(75);
		setEditRoute(Routes.PERSON_EDIT);
		ui = UI.getCurrent();
	}

	@Override
	public void setUpCustomColumns() {
		addComponentContainerProperty(new ValueProvider<Person, Image>() {

			private static final long serialVersionUID = 1818232591747121810L;

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
							logger.error(e.getMessage(), e);
						}
					}
				});
				thread.start();
				//
				return imageField;
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

		private CStringIntervalLayout lastNameFld;
		private CStringIntervalLayout firstNameFld;
		private GenderSelect genderFld;

		@Override
		public void executeBuild() {

			firstNameFld = addField(CStringIntervalLayout.class, PersonProperty.FIRST_NAME, 0, 0);

			lastNameFld = addField(CStringIntervalLayout.class, PersonProperty.LAST_NAME, 1, 0);

			genderFld = addField(GenderSelect.class, PersonProperty.GENDER, 2, 0);
			genderFld.setVisible(false);
			if (getViewDetail().getPrivileges().contains(Privileges.PERSON_SHOW_GENDER)) {
				genderFld.setVisible(true);
			}

			Person person = getIfParentEntity(Person.class);
			if (person != null) {
				firstNameFld.setValue(person.getFirstName());
				firstNameFld.setEnabled(false);
				lastNameFld.setValue(person.getLastName());
				lastNameFld.setEnabled(false);
				genderFld.setValue(person.getGender());
				genderFld.setEnabled(false);
			}

		}

		@Override
		public Object getCriteria() {
			PersonVO vo = new PersonVO();
			vo.setFirstNameInterval(firstNameFld.getValue());
			vo.setLastNameInterval(lastNameFld.getValue());
			vo.setGenderId(genderFld.getItemId());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}


}
