package software.simple.solutions.framework.core.web.view;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.ValueProvider;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CLabel;
import software.simple.solutions.framework.core.components.CPasswordField;
import software.simple.solutions.framework.core.components.CPopupDateField;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ApplicationUserServiceFacade;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.PersonInformationServiceFacade;
import software.simple.solutions.framework.core.upload.ImageField;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;
import software.simple.solutions.framework.core.web.lookup.PersonLookUpField;
import software.simple.solutions.framework.core.web.view.password.ResetPasswordLayout;

public class ApplicationUserView extends BasicTemplate<ApplicationUser> {

	private static final long serialVersionUID = 6503015064562511801L;

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
	}

	public ApplicationUserView() {
		setEntityClass(ApplicationUser.class);
		setServiceClass(ApplicationUserServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setReadonlyFormClass(ReadonlyForm.class);
		setParentReferenceKey(ReferenceKey.APPLICATION_USER);
	}

	@Override
	public void setUpCustomColumns() {
		addComponentContainerProperty(new ValueProvider<ApplicationUser, Image>() {

			private static final long serialVersionUID = -2238739724212518767L;

			@Override
			public Image apply(ApplicationUser source) {
				Image image = new Image();
				image.setSource(CxodeIcons.PROFILE_IMAGE);
				image.setWidth("75px");
				return image;
			}
		}, ApplicationUserProperty.IMAGE);
		addContainerProperty(ApplicationUser::getUsername, ApplicationUserProperty.USERNAME);
		addContainerProperty(ApplicationUser::getPerson, PersonProperty.PERSON);

		setGridRowHeight(75);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout userNameFld;
		private CStringIntervalLayout personFirstNameFld;
		private CStringIntervalLayout personLastNameFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() {

			userNameFld = addField(CStringIntervalLayout.class, ApplicationUserProperty.USERNAME, 0, 0);
			activeFld = addField(ActiveSelect.class, ApplicationUserProperty.ACTIVE, 0, 1);
			personFirstNameFld = addField(CStringIntervalLayout.class, PersonProperty.FIRST_NAME, 1, 0);
			personLastNameFld = addField(CStringIntervalLayout.class, PersonProperty.LAST_NAME, 1, 1);
		}

		@Override
		public Object getCriteria() {
			ApplicationUserVO vo = new ApplicationUserVO();
			vo.setUsernameInterval(userNameFld.getValue());
			vo.setActive(activeFld.getItemId());
			vo.setLastNameInterval(personLastNameFld.getValue());
			vo.setFirstNameInterval(personFirstNameFld.getValue());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CGridLayout userInfoLayout;
		private HorizontalLayout personInfoLayout;
		private PersonLookUpField personLookUpFld;
		private CTextField usernameFld;
		private CCheckBox activeFld;
		private CCheckBox forceChangePasswordFld;
		private CButton managePasswordBtn;
		private CTextField emailFld;

		private CGridLayout passwordLayout;
		private CPasswordField passwordFld;
		private CPasswordField confirmPasswordFld;

		private ImageField imageField;
		private CTextField lastNameFld;
		private CTextField firstNameFld;
		private CTextField middleNameFld;
		private CPopupDateField dateOfBirthFld;
		private CTextField genderFld;
		private CCheckBox isPersonActiveFld;

		private CGridLayout ldapLayout;
		private CCheckBox useLdapFld;

		private ApplicationUser applicationUser;
		private Person person;

		@Override
		public void executeBuild() throws FrameworkException {

			HorizontalLayout userMainLayout = new HorizontalLayout();
			addComponent(userMainLayout);

			userInfoLayout = createUserInfoLayout();
			userInfoLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			userInfoLayout.setCaption(PropertyResolver.getPropertyValueByLocale(
					ApplicationUserProperty.APPLICATION_USER_INFO, UI.getCurrent().getLocale()));
			userMainLayout.addComponent(userInfoLayout);

			passwordLayout = createPasswordLayout();
			passwordLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			passwordLayout.setCaption(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.PASSWORD_LAYOUT,
					UI.getCurrent().getLocale()));
			userMainLayout.addComponent(passwordLayout);

			ldapLayout = createLdapLayout();
			ldapLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			ldapLayout.setCaption(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.LDAP_LAYOUT,
					UI.getCurrent().getLocale()));
			userMainLayout.addComponent(ldapLayout);
			ldapLayout.setVisible(false);

			Configuration useLdapConfig = ConfigurationServiceFacade.get(UI.getCurrent())
					.getByCode(ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP);
			if (useLdapConfig != null && useLdapConfig.getBoolean()) {
				ldapLayout.setVisible(true);
			}

			personInfoLayout = createPersonLayout();
			personInfoLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			personInfoLayout.setCaption(
					PropertyResolver.getPropertyValueByLocale(PersonProperty.PERSON_INFO, UI.getCurrent().getLocale()));
			personInfoLayout.setMargin(true);
			addComponent(personInfoLayout);

			createPersonLookUpListener();
		}

		private CGridLayout createUserInfoLayout() {
			// common part: create layout
			userInfoLayout = ComponentUtil.createGrid();
			userInfoLayout.setWidth("-1px");
			userInfoLayout.setHeight("-1px");
			userInfoLayout.setMargin(true);
			userInfoLayout.setSpacing(true);

			personLookUpFld = userInfoLayout.addField(PersonLookUpField.class, PersonProperty.PERSON, 0, 0);
			userInfoLayout.setComponentAlignment(personLookUpFld, Alignment.BOTTOM_LEFT);

			// usernameFld
			usernameFld = userInfoLayout.addField(CTextField.class, ApplicationUserProperty.USERNAME, 0, 1);
			usernameFld.setWidth("250px");

			// emailFld
			emailFld = userInfoLayout.addField(CTextField.class, ApplicationUserProperty.EMAIL, 0, 2);
			emailFld.setWidth("250px");

			// isActiveChkBox
			activeFld = userInfoLayout.addField(CCheckBox.class, ApplicationUserProperty.ACTIVE, 1, 0);
			activeFld.addStyleName(ValoTheme.CHECKBOX_LARGE);
			userInfoLayout.setComponentAlignment(activeFld, Alignment.MIDDLE_CENTER);

			return userInfoLayout;
		}

		private CGridLayout createPasswordLayout() {
			passwordLayout = ComponentUtil.createGrid();
			passwordLayout.setMargin(true);
			passwordLayout.setSpacing(true);

			forceChangePasswordFld = passwordLayout.addField(CCheckBox.class,
					ApplicationUserProperty.FORCE_CHANGE_PASSWORD, 0, 0);
			forceChangePasswordFld.addStyleName(ValoTheme.CHECKBOX_LARGE);
			passwordLayout.setComponentAlignment(forceChangePasswordFld, Alignment.BOTTOM_LEFT);

			passwordFld = passwordLayout.addField(CPasswordField.class, ApplicationUserProperty.PASSWORD, 0, 1);
			passwordFld.setVisible(false);

			confirmPasswordFld = passwordLayout.addField(CPasswordField.class, ApplicationUserProperty.PASSWORD_CONFIRM,
					0, 2);
			confirmPasswordFld.setVisible(false);

			// managePasswordBtn
			managePasswordBtn = new CButton();
			passwordLayout.addComponent(managePasswordBtn, 0, 3, 1, 3);
			managePasswordBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
			managePasswordBtn.setCaptionByKey(SystemProperty.SYSTEM_RESET_PASSWORD);
			passwordLayout.setComponentAlignment(managePasswordBtn, Alignment.BOTTOM_LEFT);

			return passwordLayout;
		}

		private CGridLayout createLdapLayout() {
			ldapLayout = ComponentUtil.createGrid();
			ldapLayout.setMargin(true);
			ldapLayout.setSpacing(true);

			useLdapFld = ldapLayout.addField(CCheckBox.class, ApplicationUserProperty.USE_LDAP, 0, 0);
			useLdapFld.addStyleName(ValoTheme.CHECKBOX_LARGE);
			ldapLayout.setComponentAlignment(useLdapFld, Alignment.BOTTOM_LEFT);

			return ldapLayout;
		}

		private HorizontalLayout createPersonLayout() {
			personInfoLayout = new HorizontalLayout();

			imageField = new ImageField();
			personInfoLayout.addComponent(imageField);

			formGrid = ComponentUtil.createGrid();
			personInfoLayout.addComponent(formGrid);

			firstNameFld = formGrid.addField(CTextField.class, PersonProperty.FIRST_NAME, 0, 0);
			firstNameFld.setReadOnly(true);

			middleNameFld = formGrid.addField(CTextField.class, PersonProperty.MIDDLE_NAME, 0, 1);
			middleNameFld.setReadOnly(true);

			lastNameFld = formGrid.addField(CTextField.class, PersonProperty.LAST_NAME, 0, 2);
			lastNameFld.setReadOnly(true);

			dateOfBirthFld = formGrid.addField(CPopupDateField.class, PersonProperty.DATE_OF_BIRTH, 1, 0);
			dateOfBirthFld.setReadOnly(true);

			genderFld = formGrid.addField(CTextField.class, GenderProperty.GENDER, 1, 1);
			genderFld.setReadOnly(true);

			isPersonActiveFld = formGrid.addField(CCheckBox.class, PersonProperty.ACTIVE, 1, 2);
			isPersonActiveFld.setReadOnly(true);
			formGrid.setComponentAlignment(isPersonActiveFld, Alignment.BOTTOM_LEFT);

			return personInfoLayout;
		}

		private void createPersonLookUpListener() {
			personInfoLayout.setVisible(false);
			personLookUpFld.addValueChangeListener(new ValueChangeListener<Object>() {

				private static final long serialVersionUID = -690002121523153332L;

				@Override
				public void valueChange(ValueChangeEvent<Object> event) {
					person = (Person) event.getValue();
					personInfoLayout.setVisible(false);
					if (person != null) {
						personInfoLayout.setVisible(true);
						setPersonInfo();
						try {
							String email = PersonInformationServiceFacade.get(UI.getCurrent()).getEmail(person.getId());
							emailFld.setValue(email);
						} catch (FrameworkException e) {
							new MessageWindowHandler(e);
						}
					}
				}
			});
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);
			forceChangePasswordFld.setValue(true);
			passwordFld.setVisible(true);
			confirmPasswordFld.setVisible(true);
			managePasswordBtn.setVisible(false);

			useLdapFld.addValueChangeListener(new ValueChangeListener<Boolean>() {

				private static final long serialVersionUID = 97778041366724797L;

				@Override
				public void valueChange(ValueChangeEvent<Boolean> event) {
					Boolean selected = event.getValue();
					if (selected) {
						passwordLayout.setVisible(false);
					} else {
						passwordLayout.setVisible(true);
					}
				}
			});
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public ApplicationUser setFormValues(Object entity) throws FrameworkException {
			applicationUser = (ApplicationUser) entity;
			person = applicationUser.getPerson();
			addToReferenceKey(ReferenceKey.PERSON, person);

			usernameFld.setReadOnly(true);
			usernameFld.setValue(applicationUser.getUsername());
			activeFld.setValue(applicationUser.getActive());
			forceChangePasswordFld.setValue(applicationUser.getResetPassword());
			personLookUpFld.setValue(person);

			passwordFld.setVisible(false);
			confirmPasswordFld.setVisible(false);

			managePasswordBtn.addClickListener(new ClickListener() {

				private static final long serialVersionUID = -2155112159120300302L;

				@Override
				public void buttonClick(ClickEvent event) {
					String email = emailFld.getValue();
					try {
						boolean smtpEnabled = ConfigurationServiceFacade.get(UI.getCurrent()).isSmtpEnabled();
						if (!smtpEnabled || StringUtils.isBlank(email)) {
							new ResetPasswordLayout(applicationUser.getId());
						} else {
							resetPasswordAndSendMail();
						}
					} catch (FrameworkException e) {
						new MessageWindowHandler(e);
					}
				}
			});

			useLdapFld.addValueChangeListener(new ValueChangeListener<Boolean>() {

				private static final long serialVersionUID = 97778041366724797L;

				@Override
				public void valueChange(ValueChangeEvent<Boolean> event) {
					Boolean selected = event.getValue();
					if (selected) {
						passwordLayout.setVisible(false);
					} else {
						passwordLayout.setVisible(true);
					}
				}
			});
			useLdapFld.setValue(applicationUser.getUseLdap());

			return applicationUser;
		}
		
		protected void resetPasswordAndSendMail() throws FrameworkException {
			applicationUser = ApplicationUserServiceFacade.get(UI.getCurrent())
					.resetUserPassword(applicationUser.getId(), sessionHolder.getApplicationUser().getId());
			setFormValues(applicationUser);
			NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
		}

		private void setPersonInfo() {
			firstNameFld.setValue(person.getFirstName());
			middleNameFld.setValue(person.getMiddleName());
			lastNameFld.setValue(person.getLastName());
			dateOfBirthFld.setValue(person.getDateOfBirth());
			genderFld.setValue(person.getGender() == null ? null
					: PropertyResolver.getPropertyValueByLocale(person.getGender().getKey(),
							UI.getCurrent().getLocale()));
			isPersonActiveFld.setValue(person.getActive());
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			ApplicationUserVO vo = new ApplicationUserVO();
			vo.setActive(activeFld.getValue());
			vo.setPersonId(personLookUpFld.getItemId());
			vo.setResetPassword(forceChangePasswordFld.getValue());
			vo.setUsername(usernameFld.getValue());
			vo.setId(applicationUser == null ? null : applicationUser.getId());
			vo.setEmail(emailFld.getValue());
			vo.setUseLdap(useLdapFld.getValue());
			if (applicationUser == null) {
				vo.setPassword(passwordFld.getValue());
				vo.setPasswordConfirm(confirmPasswordFld.getValue());
			}
			return vo;
		}

	}

	public class ReadonlyForm extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CGridLayout userInfoLayout;
		private HorizontalLayout personInfoLayout;
		private CLabel personLookUpFld;
		private CLabel usernameFld;
		private CCheckBox activeFld;
		private CCheckBox forceChangePasswordFld;
		private CLabel emailFld;

		private CGridLayout passwordLayout;

		private Image imageFld;
		private CLabel lastNameFld;
		private CLabel firstNameFld;
		private CLabel middleNameFld;
		private CLabel dateOfBirthFld;
		private CLabel genderFld;
		private CCheckBox isPersonActiveFld;

		private CGridLayout ldapLayout;
		private CCheckBox useLdapFld;

		private ApplicationUser applicationUser;
		private Person person;

		@Override
		public void executeBuild() throws FrameworkException {

			HorizontalLayout userMainLayout = new HorizontalLayout();
			addComponent(userMainLayout);

			personInfoLayout = createPersonLayout();
			personInfoLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			personInfoLayout.setCaption(
					PropertyResolver.getPropertyValueByLocale(PersonProperty.PERSON_INFO, UI.getCurrent().getLocale()));
			personInfoLayout.setMargin(true);
			userMainLayout.addComponent(personInfoLayout);

			userInfoLayout = createUserInfoLayout();
			userInfoLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			userInfoLayout.setCaption(PropertyResolver.getPropertyValueByLocale(
					ApplicationUserProperty.APPLICATION_USER_INFO, UI.getCurrent().getLocale()));
			userMainLayout.addComponent(userInfoLayout);

			passwordLayout = createPasswordLayout();
			passwordLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			passwordLayout.setCaption(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.PASSWORD_LAYOUT,
					UI.getCurrent().getLocale()));
			userMainLayout.addComponent(passwordLayout);

			ldapLayout = createLdapLayout();
			ldapLayout.addStyleName(ValoTheme.LAYOUT_CARD);
			ldapLayout.setCaption(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.LDAP_LAYOUT,
					UI.getCurrent().getLocale()));
			userMainLayout.addComponent(ldapLayout);
			ldapLayout.setVisible(false);

			Configuration useLdapConfig = ConfigurationServiceFacade.get(UI.getCurrent())
					.getByCode(ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP);
			if (useLdapConfig != null && useLdapConfig.getBoolean()) {
				ldapLayout.setVisible(true);
			}
		}

		private CGridLayout createUserInfoLayout() {
			// common part: create layout
			userInfoLayout = ComponentUtil.createGrid();
			userInfoLayout.setWidth("-1px");
			userInfoLayout.setHeight("-1px");
			userInfoLayout.setMargin(true);
			userInfoLayout.setSpacing(true);

			personLookUpFld = userInfoLayout.addField(CLabel.class, PersonProperty.PERSON, 0, 0);
			userInfoLayout.setComponentAlignment(personLookUpFld, Alignment.BOTTOM_LEFT);

			// usernameFld
			usernameFld = userInfoLayout.addField(CLabel.class, ApplicationUserProperty.USERNAME, 0, 1);
			usernameFld.setWidth("250px");

			// emailFld
			emailFld = userInfoLayout.addField(CLabel.class, ApplicationUserProperty.EMAIL, 0, 2);
			emailFld.setWidth("250px");

			// isActiveChkBox
			activeFld = userInfoLayout.addField(CCheckBox.class, ApplicationUserProperty.ACTIVE, 1, 0);
			activeFld.setReadOnly(true);
			activeFld.addStyleName(ValoTheme.CHECKBOX_LARGE);
			userInfoLayout.setComponentAlignment(activeFld, Alignment.MIDDLE_CENTER);

			return userInfoLayout;
		}

		private CGridLayout createPasswordLayout() {
			passwordLayout = ComponentUtil.createGrid();
			passwordLayout.setMargin(true);
			passwordLayout.setSpacing(true);

			forceChangePasswordFld = passwordLayout.addField(CCheckBox.class,
					ApplicationUserProperty.FORCE_CHANGE_PASSWORD, 0, 0);
			forceChangePasswordFld.setReadOnly(true);
			forceChangePasswordFld.addStyleName(ValoTheme.CHECKBOX_LARGE);
			passwordLayout.setComponentAlignment(forceChangePasswordFld, Alignment.BOTTOM_LEFT);

			return passwordLayout;
		}

		private CGridLayout createLdapLayout() {
			ldapLayout = ComponentUtil.createGrid();
			ldapLayout.setMargin(true);
			ldapLayout.setSpacing(true);

			useLdapFld = ldapLayout.addField(CCheckBox.class, ApplicationUserProperty.USE_LDAP, 0, 0);
			useLdapFld.setReadOnly(true);
			useLdapFld.addStyleName(ValoTheme.CHECKBOX_LARGE);
			ldapLayout.setComponentAlignment(useLdapFld, Alignment.BOTTOM_LEFT);

			return ldapLayout;
		}

		private HorizontalLayout createPersonLayout() {
			personInfoLayout = new HorizontalLayout();

			imageFld = new Image();
			personInfoLayout.addComponent(imageFld);

			formGrid = ComponentUtil.createGrid();
			personInfoLayout.addComponent(formGrid);

			firstNameFld = formGrid.addField(CLabel.class, PersonProperty.FIRST_NAME, 0, 0);

			middleNameFld = formGrid.addField(CLabel.class, PersonProperty.MIDDLE_NAME, 0, 1);

			lastNameFld = formGrid.addField(CLabel.class, PersonProperty.LAST_NAME, 0, 2);

			dateOfBirthFld = formGrid.addField(CLabel.class, PersonProperty.DATE_OF_BIRTH, 1, 0);

			genderFld = formGrid.addField(CLabel.class, GenderProperty.GENDER, 1, 1);

			isPersonActiveFld = formGrid.addField(CCheckBox.class, PersonProperty.ACTIVE, 1, 2);
			isPersonActiveFld.setReadOnly(true);
			formGrid.setComponentAlignment(isPersonActiveFld, Alignment.BOTTOM_LEFT);

			return personInfoLayout;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ApplicationUser setFormValues(Object entity) throws FrameworkException {
			applicationUser = (ApplicationUser) entity;
			person = applicationUser.getPerson();
			addToReferenceKey(ReferenceKey.APPLICATION_USER, applicationUser);
				addToReferenceKey(ReferenceKey.PERSON, person);

			usernameFld.setValue(applicationUser.getUsername());
			activeFld.setValue(applicationUser.getActive());
			forceChangePasswordFld.setValue(applicationUser.getResetPassword());
			personLookUpFld.setValue(person == null ? null : person.getCaption());
			useLdapFld.setValue(applicationUser.getUseLdap());
			ThemeResource resource = new ThemeResource("img/profile-pic-300px.jpg");
			imageFld.setSource(resource);
			imageFld.setHeight("120px");

			if (person != null) {
				String email = PersonInformationServiceFacade.get(UI.getCurrent()).getEmail(person.getId());
				emailFld.setValue(email);
			}

			setPersonInfo();

			return applicationUser;
		}

		private void setPersonInfo() {
			personInfoLayout.setVisible(false);
			if (person != null) {
				personInfoLayout.setVisible(true);
				firstNameFld.setValue(person.getFirstName());
				middleNameFld.setValue(person.getMiddleName());
				lastNameFld.setValue(person.getLastName());

				dateOfBirthFld.setValue(person.getDateOfBirth() == null ? null
						: person.getDateOfBirth().format(Constants.DATE_FORMATTER));
				genderFld.setValue(person.getGender() == null ? null
						: PropertyResolver.getPropertyValueByLocale(person.getGender().getKey(),
								UI.getCurrent().getLocale()));
				isPersonActiveFld.setValue(person.getActive());
			}
		}
	}

}
