package software.simple.solutions.framework.core.web.view.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.MimeType;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IFileService;
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
	private Image logoImageHolder;
	private Upload upload;
	private UploadFieldReceiver receiver;

	private CButton persistBtn;
	private SessionHolder sessionHolder;
	private UI ui;

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

		logoImageHolder = createLogoImageHolder();
		VerticalLayout logoImageLayout = addField(VerticalLayout.class, ConfigurationProperty.APPLICATION_LOGO, 0, ++i);
		logoImageLayout.addComponent(logoImageHolder);
		receiver = new FileBuffer();

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
				logoImageHolder.setSource(resource);
			}
		});

		persistBtn = addField(CButton.class, 0, ++i);
		persistBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		persistBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);

		persistBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 3418895031034445319L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<ConfigurationVO> configurations = new ArrayList<ConfigurationVO>();
				configurations.add(getValue(ConfigurationProperty.APPLICATION_NAME, applicationNameFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_URL, applicationURLFld.getValue()));
				configurations.add(
						getValue(ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT, exportRowCountFld.getValue()));
				configurations.add(getValue(ConfigurationProperty.APPLICATION_DATE_FORMAT, dateFormatFld.getValue()));

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

				IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
				try {
					configurationService.update(configurations);
					NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
				}
			}
		});

		setValues();
	}

	private Image createLogoImageHolder() {
		Image image = new Image();
		image.setWidth("200px");
		image.setHeight("40px");
		image.setSource(new ThemeResource("../cxode/img/your-logo-here.png"));
		image.addStyleName(Style.MAIN_VIEW_APPLICATION_LOGO);
		return image;
	}

	private void setValues() throws FrameworkException {
		IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
		List<Configuration> configurations = configurationService.getApplicationConfiguration();
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
				Resource resource = new StreamResource(new StreamSource() {

					private static final long serialVersionUID = 7941811283553249939L;

					@Override
					public InputStream getStream() {
						IFileService fileService = ContextProvider.getBean(IFileService.class);
						try {
							EntityFile entityFile = fileService.findFileByEntityAndType(
									configuration.getId().toString(), Configuration.class.getName(),
									ConfigurationProperty.APPLICATION_LOGO);
							if (entityFile!=null && entityFile.getFileObject() != null) {
								return new ByteArrayInputStream(entityFile.getFileObject());
							}
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
						}
						return null;
					}
				}, UUID.randomUUID().toString());
				logoImageHolder.setSource(resource);
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
