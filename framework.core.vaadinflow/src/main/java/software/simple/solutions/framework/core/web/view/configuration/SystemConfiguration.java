package software.simple.solutions.framework.core.web.view.configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.StartedEvent;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.components.select.LayoutSelect;
import software.simple.solutions.framework.core.components.select.RoleSelect;
import software.simple.solutions.framework.core.config.SystemObserver;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.MimeType;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.FileServiceFacade;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CConfigurationLayout;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.NotificationBuilder;

@CxodeConfigurationComponent(order = 1, captionKey = ConfigurationProperty.APPLICATION_CONFIGURATION)
public class SystemConfiguration extends CConfigurationLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(SystemConfiguration.class);

	private CTextField applicationNameFld;
	private CTextField applicationURLFld;
	private CDiscreetNumberField exportRowCountFld;
	private CTextField dateFormatFld;
	private CCheckBox consolidateRoleFld;
	private Upload upload;
	private MemoryBuffer buffer;
	private CDiscreetNumberField applicationLogoHeight;
	private CDiscreetNumberField applicationLogoWidth;
	private CCheckBox enableRegistrationFld;
	private RoleSelect defaultUserRoleFld;
	private CComboBox homeViewFld;
	private LayoutSelect layoutFld;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;
	private Configuration applicationLogoConfiguration;
	private CButton deleteImgBtn;
	private Image applicationLogoImage;

	public SystemConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		applicationNameFld = add(CTextField.class, ConfigurationProperty.APPLICATION_NAME);
		applicationURLFld = add(CTextField.class, ConfigurationProperty.APPLICATION_URL);
		exportRowCountFld = add(CDiscreetNumberField.class, ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT);
		dateFormatFld = add(CTextField.class, ConfigurationProperty.APPLICATION_DATE_FORMAT);
		consolidateRoleFld = add(CCheckBox.class, ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
		homeViewFld = add(CComboBox.class, ConfigurationProperty.APPLICATION_HOME_VIEW);
		setUpHomeViewFld();
		layoutFld = add(LayoutSelect.class, ConfigurationProperty.APPLICATION_LAYOUT);
		enableRegistrationFld = add(CCheckBox.class, ConfigurationProperty.APPLICATION_ENABLE_REGISTRATION);
		defaultUserRoleFld = add(RoleSelect.class, ConfigurationProperty.APPLICATION_DEFAULT_USER_ROLE);

		HorizontalLayout horizontalLayout = createLogoImageHolder();
		VerticalLayout logoImageLayout = add(VerticalLayout.class, ConfigurationProperty.APPLICATION_LOGO);
		logoImageLayout.add(horizontalLayout);

		applicationLogoHeight = add(CDiscreetNumberField.class, ConfigurationProperty.APPLICATION_LOGO_HEIGHT);
		applicationLogoHeight.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<Integer>>() {

			@Override
			public void valueChanged(ValueChangeEvent<Integer> event) {
				applicationLogoImage.setHeight("40px");
				Long height = applicationLogoHeight.getLongValue();
				if (height != null && height.compareTo(0L) > 0) {
					applicationLogoImage.setHeight(height + "px");
				}
			}
		});
		applicationLogoWidth = add(CDiscreetNumberField.class, ConfigurationProperty.APPLICATION_LOGO_WIDTH);
		applicationLogoWidth.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<Integer>>() {

			@Override
			public void valueChanged(ValueChangeEvent<Integer> event) {
				applicationLogoImage.setWidth("200px");
				Long width = applicationLogoWidth.getLongValue();
				if (width != null && width.compareTo(0L) > 0) {
					applicationLogoImage.setWidth(width + "px");
				}
			}
		});

		buffer = new MemoryBuffer();
		upload = new Upload(buffer);
		logoImageLayout.add(upload);
		upload.addStartedListener(new ComponentEventListener<StartedEvent>() {

			@Override
			public void onComponentEvent(StartedEvent event) {
				String mimeType = event.getMIMEType();
				if (!mimeType.equalsIgnoreCase(MimeType.MIME_IMAGE_PNG)
						&& !mimeType.equalsIgnoreCase(MimeType.MIME_IMAGE_BMP)
						&& !mimeType.equalsIgnoreCase(MimeType.MIME_IMAGE_JPEG)) {
					upload.interruptUpload();
					NotificationBuilder.buildError(
							PropertyResolver.getPropertyValueByLocale(SystemMessageProperty.INVALID_FILE_TYPE,
									ui.getLocale(), new Arg().norm(Arrays.asList(MimeType.MIME_IMAGE_PNG,
											MimeType.MIME_IMAGE_BMP, MimeType.MIME_IMAGE_JPEG))));
				}
			}
		});
		upload.addSucceededListener(new ComponentEventListener<SucceededEvent>() {

			@Override
			public void onComponentEvent(SucceededEvent event) {
				StreamResource streamResource = new StreamResource(buffer.getFileName(), new InputStreamFactory() {

					@Override
					public InputStream createInputStream() {
						return buffer.getInputStream();
					}
				});
				applicationLogoImage.setSrc(streamResource);
			}
		});

		persistBtn = add(CButton.class, SystemProperty.SYSTEM_BUTTON_SUBMIT);
		persistBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		persistBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.APPLICATION_NAME, applicationNameFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_URL, applicationURLFld.getValue()));
				configurations.add(
						getValue(ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT, exportRowCountFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_DATE_FORMAT, dateFormatFld.getValue()));
				configurations.add(
						getValue(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE, consolidateRoleFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_ENABLE_REGISTRATION,
						enableRegistrationFld.getValue()));
				configurations
						.add(getValue(ConfigurationProperty.APPLICATION_LOGO_HEIGHT, applicationLogoHeight.getValue()));
				configurations
						.add(getValue(ConfigurationProperty.APPLICATION_LOGO_WIDTH, applicationLogoWidth.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_HOME_VIEW, homeViewFld.getStringValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_LAYOUT, layoutFld.getStringValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_DEFAULT_USER_ROLE,
						defaultUserRoleFld.getStringValue()));
				// try {
				// if (buffer.getFileData().getLastFileSize() > 0) {
				// InputStream inputStream = receiver.getContentAsStream();
				// byte[] fileBytes = new byte[(int)
				// receiver.getLastFileSize()];
				// inputStream.read(fileBytes);
				// inputStream.close();
				// configurations.add(getValue(ConfigurationProperty.APPLICATION_LOGO,
				// fileBytes));
				// }
				// } catch (IOException e) {
				// logger.error(e.getMessage(), e);
				// }

				try {
					List<Configuration> configs = ConfigurationServiceFacade.get(UI.getCurrent())
							.update(configurations);
					if (configs != null) {
						// application logo
						List<String> configurationCodes = configs.stream()
								.filter(p -> (p.getCode().equalsIgnoreCase(ConfigurationProperty.APPLICATION_LOGO)
										|| p.getCode().equalsIgnoreCase(ConfigurationProperty.APPLICATION_LOGO_HEIGHT)
										|| p.getCode().equalsIgnoreCase(ConfigurationProperty.APPLICATION_LOGO_WIDTH)))
								.map(p -> p.getCode()).collect(Collectors.toList());
						if (configurationCodes != null && !configurationCodes.isEmpty()) {
							if (configurationCodes.contains(ConfigurationProperty.APPLICATION_LOGO)) {
								deleteImgBtn.setVisible(true);
							}
							SystemObserver systemObserver = ContextProvider.getBean(SystemObserver.class);
							systemObserver.getApplicationLogoChangeObserver().onNext(true);
						}

						// role consolidation
						configurationCodes = configs.stream().filter(
								p -> p.getCode().equalsIgnoreCase(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE))
								.map(p -> p.getCode()).collect(Collectors.toList());
						if (configurationCodes != null && !configurationCodes.isEmpty()) {
							SystemObserver systemObserver = ContextProvider.getBean(SystemObserver.class);
							systemObserver.getApplicationConsolidateRoleChangeObserver().onNext(true);
						}
					}
					NotificationBuilder.buildSuccess(PropertyResolver
							.getPropertyValueByLocale(SystemProperty.UPDATE_SUCCESSFULL, UI.getCurrent().getLocale()));
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					DetailsWindow.build(e);
				}

			}
		});

		setValues();
	}

	private void setUpHomeViewFld() throws FrameworkException {
		MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());
		List<Menu> menus = menuServiceFacade.getPossibleHomeViews();
		if (menus != null) {
			List<ComboItem> items = new ArrayList<ComboItem>();
			for (Menu menu : menus) {
				ComboItem comboItem = new ComboItem(menu.getId());
				comboItem.setName(PropertyResolver.getPropertyValueByLocale(ReferenceKey.MENU, menu.getId(),
						UI.getCurrent().getLocale(), menu.getName()));
				items.add(comboItem);
			}
			homeViewFld.setItems(items);
		}
	}

	private HorizontalLayout createLogoImageHolder() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		applicationLogoImage = new Image();
		horizontalLayout.add(applicationLogoImage);
		applicationLogoImage.setWidth("200px");
		applicationLogoImage.setHeight("40px");
		applicationLogoImage.setSrc("img/profile-pic-300px.jpg");

		deleteImgBtn = new CButton();
		horizontalLayout.add(deleteImgBtn);
		deleteImgBtn.setVisible(false);
		deleteImgBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_DELETE);
		deleteImgBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		deleteImgBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (applicationLogoConfiguration != null) {
					MessageDialog confirmDialog = new MessageDialog();
					confirmDialog.setWidth("400px");
					Icon icon = VaadinIcon.WARNING.create();
					icon.setColor("#FF0000");
					confirmDialog.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.DELETE_HEADER,
							UI.getCurrent().getLocale()), icon);
					confirmDialog.setMessage(PropertyResolver.getPropertyValueByLocale(
							SystemProperty.DELETE_CONFIRMATION_REQUEST, UI.getCurrent().getLocale()));
					confirmDialog.addButton()
							.text(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_OK,
									ui.getLocale()))
							.primary().onClick(new ComponentEventListener<ClickEvent<Button>>() {

								@Override
								public void onComponentEvent(ClickEvent<Button> event) {
									try {
										FileServiceFacade.get(UI.getCurrent()).deleteFileByEntityAndType(
												applicationLogoConfiguration.getId().toString(),
												Configuration.class.getName(), ConfigurationProperty.APPLICATION_LOGO);
										applicationLogoImage.setSrc("../cxode/img/your-logo-here.png");
										SystemObserver systemObserver = ContextProvider.getBean(SystemObserver.class);
										systemObserver.getApplicationLogoChangeObserver().onNext(true);
									} catch (FrameworkException e) {
										logger.error(e.getMessage(), e);
									}
								}
							}).closeOnClick();
					confirmDialog
							.addButtonToLeft().text(PropertyResolver
									.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_CANCEL, ui.getLocale()))
							.tertiary().closeOnClick();
				}
			}
		});

		return horizontalLayout;
	}

	private void setValues() throws FrameworkException {
		List<Configuration> configurations = ConfigurationServiceFacade.get(UI.getCurrent())
				.getApplicationConfiguration();
		configurations.forEach(configuration -> {
			switch (configuration.getCode()) {
			case ConfigurationProperty.APPLICATION_NAME:
				applicationNameFld.setValue(configuration.getValue());
				break;
			case ConfigurationProperty.APPLICATION_URL:
				applicationURLFld.setValue(configuration.getValue());
				break;
			case ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT:
				exportRowCountFld.setLongValue(configuration.getLong());
				break;
			case ConfigurationProperty.APPLICATION_DATE_FORMAT:
				dateFormatFld.setValue(configuration.getValue());
				break;
			case ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE:
				consolidateRoleFld.setValue(configuration.getBoolean());
				break;
			case ConfigurationProperty.APPLICATION_HOME_VIEW:
				homeViewFld.setValue(configuration.getLong());
				break;
			case ConfigurationProperty.APPLICATION_LAYOUT:
				layoutFld.setValue(configuration.getLong());
				break;
			case ConfigurationProperty.APPLICATION_ENABLE_REGISTRATION:
				enableRegistrationFld.setValue(configuration.getBoolean());
				break;
			case ConfigurationProperty.APPLICATION_DEFAULT_USER_ROLE:
				defaultUserRoleFld.setLongValue(configuration.getLong());
				break;
			case ConfigurationProperty.APPLICATION_LOGO:
				applicationLogoConfiguration = configuration;
				StreamResource streamResource = new StreamResource(UUID.randomUUID().toString(),
						new InputStreamFactory() {

							@Override
							public InputStream createInputStream() {
								try {
									EntityFile entityFile = FileServiceFacade.get(UI.getCurrent())
											.findFileByEntityAndType(configuration.getId().toString(),
													Configuration.class.getName(),
													ConfigurationProperty.APPLICATION_LOGO);
									if (entityFile != null && entityFile.getFileObject() != null) {
										deleteImgBtn.setVisible(true);
										return new ByteArrayInputStream(entityFile.getFileObject());
									}
								} catch (FrameworkException e) {
									logger.error(e.getMessage(), e);
								}
								applicationLogoImage.setSrc("../cxode/img/your-logo-here.png");
								return null;
							}
						});
				applicationLogoImage.setSrc(streamResource);
				break;
			case ConfigurationProperty.APPLICATION_LOGO_HEIGHT:
				applicationLogoHeight.setLongValue(configuration.getLong() == null ? 40L : configuration.getLong());
				break;
			case ConfigurationProperty.APPLICATION_LOGO_WIDTH:
				applicationLogoWidth.setLongValue(configuration.getLong() == null ? 200L : configuration.getLong());
				break;
			default:
				break;
			}
		});
	}

	private ConfigurationVO getValue(String code, Object value) {
		ConfigurationVO vo = new ConfigurationVO();
		vo.setCode(code);
		if (value instanceof byte[]) {
			vo.setByteValue((byte[]) value);
		} else {
			vo.setValue(value == null ? null : value.toString());
		}

		vo.setUpdatedBy(sessionHolder.getApplicationUser().getId());
		vo.setUpdatedDate(LocalDateTime.now());
		return vo;
	}

}
