package software.simple.solutions.framework.core.web.view.forms;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.github.appreciated.card.Card;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;

import software.simple.solutions.framework.core.components.select.CountrySelect;
import software.simple.solutions.framework.core.components.select.GenderSelect;
import software.simple.solutions.framework.core.constants.FileReference;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonEmergencyContact;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.pojo.SortQuery;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.service.IPersonEmergencyContactService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.PersonEmergencyContactVO;
import software.simple.solutions.framework.core.valueobjects.PersonVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CEmailField;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CPopupDateField;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.GridFormCard;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.PERSON_EDIT, layout = MainView.class)
public class PersonForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private Image imageField;
	private CTextField lastNameFld;
	private CTextField firstNameFld;
	private CTextField middleNameFld;
	private CPopupDateField dateOfBirthFld;
	private GenderSelect genderFld;
	private CCheckBox activeFld;
	private HorizontalLayout mainLayout;

	private List<ComboItem> genderListing;
	private Person person;

	private Panel personInfoCard;

	private VerticalLayout personMainLayout;

	private Panel contactInfoCard;
	private CFormLayout contactInformationForm;
	private GridFormCard emergencyContactInfoCard;
	private CFormLayout emergencyContactInformationForm;

	private CEmailField primaryEmailFld;
	private CEmailField secondaryEmailFld;
	private CTextField primaryContactNumberFld;
	private CTextField secondaryContactNumberFld;
	private CTextField streetAddressFld;
	private CTextField cityFld;
	private CTextField stateFld;
	private CTextField postalCodeFld;
	private CountrySelect countryFld;

	private PagingResult<PersonEmergencyContact> pagingResult;

	private IPersonEmergencyContactService personEmergencyContactService;

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

		personEmergencyContactService = ContextProvider.getBean(IPersonEmergencyContactService.class);
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

					}
				});
			}
		});

		Tabs tabs = new Tabs();
		tabs.setOrientation(Orientation.VERTICAL);
		navigationLayout.add(tabs);
		Tab contactInformationTab = new Tab(PropertyResolver
				.getPropertyValueByLocale(PersonProperty.CONTACT_INFORMATION_TAB, UI.getCurrent().getLocale()));
		tabs.add(contactInformationTab);
		Tab emergencyContactInformationTab = new Tab(PropertyResolver.getPropertyValueByLocale(
				PersonProperty.EMERGENCY_CONTACT_INFORMATION_TAB, UI.getCurrent().getLocale()));
		tabs.add(emergencyContactInformationTab);

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(contactInformationTab, contactInfoCard);
		tabs.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {

			private static final long serialVersionUID = -3044263375186039276L;

			@Override
			public void onComponentEvent(SelectedChangeEvent event) {
				// TODO Auto-generated method stub

			}
		});

		personMainLayout = new VerticalLayout();
		personMainLayout.setWidthFull();
		mainLayout.add(personMainLayout);
		mainLayout.expand(personMainLayout);

		personInfoCard = createPersonLayout();
		personInfoCard.setWidthFull();
		personMainLayout.add(personInfoCard);

		contactInfoCard = createContactInformationLayout();
		contactInfoCard.setWidthFull();
		personMainLayout.add(contactInfoCard);

		emergencyContactInfoCard = createEmergencyContactInformationLayout();
		emergencyContactInfoCard.setWidthFull();
		personMainLayout.add(emergencyContactInfoCard);
	}

	private Panel createPersonLayout() {
		// imageField.setUploadHandler(new UploadImage() {
		//
		// @Override
		// public void upload(InputStream inputStream) {
		// try {
		// byte[] byteArray = IOUtils.toByteArray(inputStream);
		// IFileService fileService =
		// ContextProvider.getBean(IFileService.class);
		// EntityFileVO entityFileVO = new EntityFileVO();
		// entityFileVO.setActive(true);
		// entityFileVO.setDatabase(true);
		// entityFileVO.setFilename(person.getCode() + ".png");
		// entityFileVO.setEntityName(ReferenceKey.PERSON);
		// entityFileVO.setEntityId(person.getId().toString());
		// entityFileVO.setTypeOfFile(FileReference.USER_PROFILE_IMAGE);
		// entityFileVO.setFileObject(byteArray);
		// fileService.upLoadFile(entityFileVO);
		// } catch (IOException | FrameworkException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// });

		personInfoCard = new Panel();
		personInfoCard.setHeaderKey(PersonProperty.PERSON_INFO);
		formGrid = new CFormLayout();
		formGrid.setMaxWidth("800px");
		personInfoCard.add(formGrid);
		// personMainLayout.add(personInfoCard);
		// mainLayout.setFlexGrow(1, personInfoCard);

		firstNameFld = formGrid.add(CTextField.class, PersonProperty.FIRST_NAME);

		middleNameFld = formGrid.add(CTextField.class, PersonProperty.MIDDLE_NAME);

		lastNameFld = formGrid.add(CTextField.class, PersonProperty.LAST_NAME);

		dateOfBirthFld = formGrid.add(CPopupDateField.class, PersonProperty.DATE_OF_BIRTH);
		dateOfBirthFld.setMax(LocalDate.now());
		dateOfBirthFld.setVisible(false);
		// if
		// (getViewDetail().getPrivileges().contains(Privileges.PERSON_SHOW_DOB))
		// {
		dateOfBirthFld.setVisible(true);
		// }

		genderFld = formGrid.add(GenderSelect.class, GenderProperty.GENDER);
		genderFld.setVisible(false);
		// if
		// (getViewDetail().getPrivileges().contains(Privileges.PERSON_SHOW_GENDER))
		// {
		genderFld.setVisible(true);
		// }

		activeFld = formGrid.add(CCheckBox.class, PersonProperty.ACTIVE);

		return personInfoCard;
	}

	private Panel createContactInformationLayout() {
		contactInfoCard = new Panel();
		contactInfoCard.setHeaderKey(PersonInformationProperty.PERSON_INFORMATION);
		contactInformationForm = new CFormLayout();
		contactInformationForm.setMaxWidth("800px");
		contactInfoCard.add(contactInformationForm);

		primaryEmailFld = contactInformationForm.add(CEmailField.class, PersonInformationProperty.PRIMARY_EMAIL);
		secondaryEmailFld = contactInformationForm.add(CEmailField.class, PersonInformationProperty.SECONDARY_EMAIL);
		primaryContactNumberFld = contactInformationForm.add(CTextField.class,
				PersonInformationProperty.PRIMARY_CONTACT_NUMBER);
		secondaryContactNumberFld = contactInformationForm.add(CTextField.class,
				PersonInformationProperty.SECONDARY_CONTACT_NUMBER);

		streetAddressFld = contactInformationForm.add(CTextField.class, PersonInformationProperty.STREET_ADDRESS);
		cityFld = contactInformationForm.add(CTextField.class, PersonInformationProperty.CITY);
		stateFld = contactInformationForm.add(CTextField.class, PersonInformationProperty.STATE);
		postalCodeFld = contactInformationForm.add(CTextField.class, PersonInformationProperty.POSTAL_CODE);
		countryFld = contactInformationForm.add(CountrySelect.class, PersonInformationProperty.COUNTRY);

		return contactInfoCard;
	}

	private GridFormCard createEmergencyContactInformationLayout() {
		emergencyContactInfoCard = new GridFormCard();
		emergencyContactInfoCard.setHeaderKey(PersonInformationProperty.PERSON_INFORMATION);

		Grid<PersonEmergencyContact> emergencyContactGrid = emergencyContactInfoCard.getGrid();
		Column<PersonEmergencyContact> nameColumn = emergencyContactGrid
				.addColumn(new ValueProvider<PersonEmergencyContact, String>() {

					private static final long serialVersionUID = -1015344431737583501L;

					@Override
					public String apply(PersonEmergencyContact source) {
						return source.getName();
					}
				});
		nameColumn.setHeader(PropertyResolver.getPropertyValueByLocale(PersonEmergencyContactProperty.NAME,
				UI.getCurrent().getLocale()));

		Column<PersonEmergencyContact> relationshipColumn = emergencyContactGrid
				.addColumn(new ValueProvider<PersonEmergencyContact, String>() {

					private static final long serialVersionUID = -1015344431737583501L;

					@Override
					public String apply(PersonEmergencyContact source) {
						return source.getRelationship();
					}
				});
		relationshipColumn.setHeader(PropertyResolver
				.getPropertyValueByLocale(PersonEmergencyContactProperty.RELATIONSHIP, UI.getCurrent().getLocale()));

		Column<PersonEmergencyContact> contactNumberColumn = emergencyContactGrid
				.addColumn(new ValueProvider<PersonEmergencyContact, String>() {

					private static final long serialVersionUID = -1015344431737583501L;

					@Override
					public String apply(PersonEmergencyContact source) {
						return source.getContactNumber();
					}
				});
		contactNumberColumn.setHeader(PropertyResolver
				.getPropertyValueByLocale(PersonEmergencyContactProperty.CONTACT_NUMBER, UI.getCurrent().getLocale()));
		CallbackDataProvider<PersonEmergencyContact, Void> dataProvider = DataProvider
				.fromCallbacks(new CallbackDataProvider.FetchCallback<PersonEmergencyContact, Void>() {

					@Override
					public Stream<PersonEmergencyContact> fetch(Query<PersonEmergencyContact, Void> query) {
						List<SortQuery> sortOrders = new ArrayList<>();
						for (SortOrder<String> queryOrder : query.getSortOrders()) {
							sortOrders.add(new SortQuery(queryOrder.getSorted(),
									queryOrder.getDirection() == SortDirection.DESCENDING));
						}
						PagingSetting pagingSetting = new PagingSetting(query.getOffset(), query.getLimit(),
								sortOrders);

						try {
							pagingResult = personEmergencyContactService.findBySearch(createCriteria(), pagingSetting);
						} catch (FrameworkException e) {
							e.printStackTrace();
						}
						return pagingResult == null ? Stream.empty() : pagingResult.getResult().stream();
					}
				}, new CallbackDataProvider.CountCallback<PersonEmergencyContact, Void>() {

					@Override
					public int count(Query<PersonEmergencyContact, Void> query) {
						return pagingResult == null ? 1 : pagingResult.getCount().intValue();
					}
				});
		// CallbackDataProvider<PersonEmergencyContact, Void> dataProvider =
		// DataProvider.fromCallbacks(query -> {
		// List<SortQuery> sortOrders = new ArrayList<>();
		// for (SortOrder<String> queryOrder : query.getSortOrders()) {
		// sortOrders.add(
		// new SortQuery(queryOrder.getSorted(), queryOrder.getDirection() ==
		// SortDirection.DESCENDING));
		// }
		// PagingSetting pagingSetting = new PagingSetting(query.getOffset(),
		// query.getLimit(), sortOrders);
		//
		// try {
		// pagingResult =
		// personEmergencyContactService.findBySearch(createCriteria(),
		// pagingSetting);
		// } catch (FrameworkException e) {
		// e.printStackTrace();
		// }
		// return pagingResult == null ? null :
		// pagingResult.getResult().stream();
		// },
		//
		// // The number of persons is the same
		// // regardless of ordering
		// query -> pagingResult==null?0:pagingResult.getCount().intValue());
		emergencyContactGrid.setDataProvider(dataProvider);
		emergencyContactGrid.getDataProvider().withConfigurableFilter().refreshAll();

		emergencyContactGrid.setHeightByRows(true);
		// Label noRecordsFoundFld = new
		// Label(PropertyResolver.getPropertyValueByLocale(
		// SystemProperty.TOTAL_RECORDS_FOUND, UI.getCurrent().getLocale(), new
		// Object[] { 0 }));
		// verticalLayout.add(noRecordsFoundFld);
		// verticalLayout.setHorizontalComponentAlignment(Alignment.CENTER,
		// noRecordsFoundFld);

		emergencyContactInformationForm = emergencyContactInfoCard.getMainFormLayout();
		emergencyContactInformationForm.setMaxWidth("800px");

		return emergencyContactInfoCard;
	}

	private Object createCriteria() {
		// TODO Auto-generated method stub
		PersonEmergencyContactVO personEmergencyContactVO = new PersonEmergencyContactVO();
		personEmergencyContactVO.setEntityClass(PersonEmergencyContact.class);
		return personEmergencyContactVO;
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
			// imageField.setSource(new StreamResource(new StreamSource() {
			//
			// private static final long serialVersionUID =
			// -1143826625995236036L;
			//
			// @Override
			// public InputStream getStream() {
			// if (entityFile == null || entityFile.getFileObject() == null) {
			// return null;
			// }
			// return new ByteArrayInputStream(entityFile.getFileObject());
			// }
			// }, entityFile.getName()));
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
