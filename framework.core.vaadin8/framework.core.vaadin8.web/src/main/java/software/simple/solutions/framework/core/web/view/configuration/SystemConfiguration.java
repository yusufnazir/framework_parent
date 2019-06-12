package software.simple.solutions.framework.core.web.view.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.config.SystemObserver;
import software.simple.solutions.framework.core.constants.MimeType;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.FileServiceFacade;
import software.simple.solutions.framework.core.upload.FileBuffer;
import software.simple.solutions.framework.core.upload.UploadFieldReceiver;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.valueobjects.ConfigurationVO;

@CxodeConfigurationComponent(order = 1, captionKey = ConfigurationProperty.APPLICATION_CONFIGURATION)
public class SystemConfiguration extends CGridLayout {

	private static final long serialVersionUID = -4290908153354032925L;

	private static final Logger logger = LogManager.getLogger(SystemConfiguration.class);

	private CTextField applicationNameFld;
	private CTextField applicationURLFld;
	private CDiscreetNumberField exportRowCountFld;
	private CTextField dateFormatFld;
	private Upload upload;
	private UploadFieldReceiver receiver;
	private CDiscreetNumberField applicationLogoHeight;
	private CDiscreetNumberField applicationLogoWidth;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;
	private Configuration applicationLogoConfiguration;
	private CButton deleteImgBtn;
	private Image applicationLogoImage;

	public SystemConfiguration() throws FrameworkException {
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSpacing(true);
		int i = 0;
		applicationNameFld = addField(CTextField.class, ConfigurationProperty.APPLICATION_NAME, 0, ++i);
		applicationURLFld = addField(CTextField.class, ConfigurationProperty.APPLICATION_URL, 0, ++i);
		exportRowCountFld = addField(CDiscreetNumberField.class, ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT, 0,
				++i);
		dateFormatFld = addField(CTextField.class, ConfigurationProperty.APPLICATION_DATE_FORMAT, 0, ++i);

		HorizontalLayout horizontalLayout = createLogoImageHolder();
		VerticalLayout logoImageLayout = addField(VerticalLayout.class, ConfigurationProperty.APPLICATION_LOGO, 0, ++i);
		logoImageLayout.addComponent(horizontalLayout);
		receiver = new FileBuffer();

		applicationLogoHeight = addField(CDiscreetNumberField.class, ConfigurationProperty.APPLICATION_LOGO_HEIGHT, 0,
				++i);
		applicationLogoHeight.addValueChangeListener(new ValueChangeListener<String>() {

			private static final long serialVersionUID = 9054423098753143647L;

			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				applicationLogoImage.setHeight("40px");
				Long height = applicationLogoHeight.getLongValue();
				if (height != null && height.compareTo(0L) > 0) {
					applicationLogoImage.setHeight(height + "px");
				}
			}
		});
		applicationLogoWidth = addField(CDiscreetNumberField.class, ConfigurationProperty.APPLICATION_LOGO_WIDTH, 0,
				++i);
		applicationLogoWidth.addValueChangeListener(new ValueChangeListener<String>() {

			private static final long serialVersionUID = 9054423098753143647L;

			@Override
			public void valueChange(ValueChangeEvent<String> event) {
				applicationLogoImage.setWidth("200px");
				Long width = applicationLogoWidth.getLongValue();
				if (width != null && width.compareTo(0L) > 0) {
					applicationLogoImage.setWidth(width + "px");
				}
			}
		});

		upload = new Upload();
		logoImageLayout.addComponent(upload);
		upload.setImmediateMode(true);
		upload.setReceiver(receiver);
		upload.addStartedListener(new StartedListener() {

			private static final long serialVersionUID = -8237287520963974201L;

			@Override
			public void uploadStarted(StartedEvent event) {
				String mimeType = event.getMIMEType();
				if (!mimeType.equalsIgnoreCase(MimeType.MIME_IMAGE_PNG)
						&& !mimeType.equalsIgnoreCase(MimeType.MIME_IMAGE_BMP)
						&& !mimeType.equalsIgnoreCase(MimeType.MIME_IMAGE_JPEG)) {
					upload.interruptUpload();
					new MessageWindowHandler(SystemMessageProperty.INVALID_FILE_TYPE, new Arg().norm(
							Arrays.asList(MimeType.MIME_IMAGE_PNG, MimeType.MIME_IMAGE_BMP, MimeType.MIME_IMAGE_JPEG)));
				}
			}
		});
		upload.addSucceededListener(new SucceededListener() {

			private static final long serialVersionUID = -789300044622333050L;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				Resource resource = new StreamResource(new StreamSource() {

					private static final long serialVersionUID = 7941811283553249939L;

					@Override
					public InputStream getStream() {
						return receiver.getContentAsStream();
					}
				}, UUID.randomUUID().toString());
				applicationLogoImage.setSource(resource);
			}
		});

		persistBtn = addField(CButton.class, 0, ++i);
		persistBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		persistBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);

		persistBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3418895031034445319L;

			@Autowired
			@Override
			public void buttonClick(ClickEvent event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.APPLICATION_NAME, applicationNameFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_URL, applicationURLFld.getValue()));
				configurations.add(
						getValue(ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT, exportRowCountFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_DATE_FORMAT, dateFormatFld.getValue()));
				configurations
						.add(getValue(ConfigurationProperty.APPLICATION_LOGO_HEIGHT, applicationLogoHeight.getValue()));
				configurations
						.add(getValue(ConfigurationProperty.APPLICATION_LOGO_WIDTH, applicationLogoWidth.getValue()));
				try {
					if (receiver.getLastFileSize() > 0) {
						InputStream inputStream = receiver.getContentAsStream();
						byte[] fileBytes = new byte[(int) receiver.getLastFileSize()];
						inputStream.read(fileBytes);
						inputStream.close();
						configurations.add(getValue(ConfigurationProperty.APPLICATION_LOGO, fileBytes));
					}
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}

				try {
					List<Configuration> configs = ConfigurationServiceFacade.get(UI.getCurrent())
							.update(configurations);
					if (configs != null) {
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
					}
					NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		setValues();
	}

	private HorizontalLayout createLogoImageHolder() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		applicationLogoImage = new Image();
		horizontalLayout.addComponent(applicationLogoImage);
		applicationLogoImage.setWidth("200px");
		applicationLogoImage.setHeight("40px");

		applicationLogoImage.setSource(new ThemeResource("../cxode/img/your-logo-here.png"));
		applicationLogoImage.addStyleName(Style.MAIN_VIEW_APPLICATION_LOGO);

		deleteImgBtn = new CButton();
		horizontalLayout.addComponent(deleteImgBtn);
		deleteImgBtn.setVisible(false);
		deleteImgBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_DELETE);
		deleteImgBtn.addStyleName(ValoTheme.BUTTON_QUIET);
		deleteImgBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteImgBtn.addStyleName(Style.BUTTON_DANGEROUS);
		deleteImgBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 8073994137493967329L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (applicationLogoConfiguration != null) {
					ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.DELETE_HEADER,
							SystemProperty.DELETE_CONFIRMATION_REQUEST, SystemProperty.CONFIRM, SystemProperty.CANCEL);
					confirmWindow.execute(new ConfirmationHandler() {

						@Override
						public void handlePositive() {
							try {
								FileServiceFacade.get(UI.getCurrent()).deleteFileByEntityAndType(
										applicationLogoConfiguration.getId().toString(), Configuration.class.getName(),
										ConfigurationProperty.APPLICATION_LOGO);
								applicationLogoImage.setSource(new ThemeResource("../cxode/img/your-logo-here.png"));
								SystemObserver systemObserver = ContextProvider.getBean(SystemObserver.class);
								systemObserver.getApplicationLogoChangeObserver().onNext(true);
							} catch (FrameworkException e) {
								logger.error(e.getMessage(), e);
							}
						}

						@Override
						public void handleNegative() {
							return;
						}
					});
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
				exportRowCountFld.setValue(configuration.getValue());
				break;
			case ConfigurationProperty.APPLICATION_DATE_FORMAT:
				dateFormatFld.setValue(configuration.getValue());
				break;
			case ConfigurationProperty.APPLICATION_LOGO:
				applicationLogoConfiguration = configuration;
				Resource resource = new StreamResource(new StreamSource() {

					private static final long serialVersionUID = 7941811283553249939L;

					@Override
					public InputStream getStream() {
						try {
							EntityFile entityFile = FileServiceFacade.get(UI.getCurrent()).findFileByEntityAndType(
									configuration.getId().toString(), Configuration.class.getName(),
									ConfigurationProperty.APPLICATION_LOGO);
							if (entityFile != null && entityFile.getFileObject() != null) {
								deleteImgBtn.setVisible(true);
								return new ByteArrayInputStream(entityFile.getFileObject());
							}
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
						}
						applicationLogoImage.setSource(new ThemeResource("../cxode/img/your-logo-here.png"));
						return null;
					}
				}, UUID.randomUUID().toString());
				applicationLogoImage.setSource(resource);
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
