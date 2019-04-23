package software.simple.solutions.framework.core.components;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.event.ListenerMethod.MethodException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.MessagePerLocale;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.Arg.Key;
import software.simple.solutions.framework.core.exceptions.Arg.Value;
import software.simple.solutions.framework.core.exceptions.ExceptionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMessagePerLocaleService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ErrorLayout extends VerticalLayout {

	private static final long serialVersionUID = -1543377211498316257L;

	private static final Logger logger = LoggerFactory.getLogger(ErrorLayout.class);

	private static final String DEFAULT_CAPTION = "<b>An error has occurred</b>";

	private static final String DEFAULT_ERROR_LABEL_MESSAGE = "Please contact the system administrator for more information.";

	private VerticalLayout exceptionTraceLayout;

	private Throwable cause;
	private String errorMessage;
	private String messageProperty;
	private Arg args;

	public ErrorLayout(final String messageProperty, Arg args) {
		this.messageProperty = messageProperty;
		this.args = args;
		initLayout();
	}

	public ErrorLayout(final Throwable cause) {
		this.cause = cause;
		initLayout();
	}

	private void initLayout() {
		addStyleName(Style.ERROR_BACKGROUND_REDDISH);
		setWidth(500, Unit.PIXELS);
		if (cause != null) {
			ExceptionHolder exceptionHolder = null;
			if (cause instanceof FrameworkException) {
				exceptionHolder = ((FrameworkException) cause).getExceptionHolder();
			}

			if (exceptionHolder != null) {
				messageProperty = exceptionHolder.getMessageKey();
				args = exceptionHolder.getArgs();
			}
		}

		if (StringUtils.isNotBlank(messageProperty)) {
			IMessagePerLocaleService messagePerLocaleService = ContextProvider.getBean(IMessagePerLocaleService.class);
			String iso3Language = UI.getCurrent().getLocale().getISO3Language();
			MessagePerLocale messagePerLocale = null;
			try {
				messagePerLocale = messagePerLocaleService.getByMessageAndLocale(messageProperty, iso3Language);
				errorMessage = messagePerLocale.getReason();
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
			}
			if (messagePerLocale == null) {
				logger.error("No messagenumber defined for [" + messageProperty + "]");
			}

			if (StringUtils.isNotBlank(messagePerLocale.getReason())) {
				errorMessage = messagePerLocale.getReason();
			}
			if (errorMessage == null) {
				errorMessage = "No message defined";
			} else if (args != null) {
				MessageFormat messageFormat = new MessageFormat(errorMessage);
				List<Value> values = args.getValues();
				List<Object> arguments = new ArrayList<Object>();
				for (Value value : values) {
					if (value instanceof Key) {
						String valueByLocale = PropertyResolver.getPropertyValueByLocale(value.getValue());
						arguments.add(valueByLocale);
					} else {
						arguments.add(value.getValue());
					}
				}
				errorMessage = messageFormat.format(arguments.toArray());
			}
		}

		setSpacing(false);
		setMargin(true);

		final Label errorLabel = createErrorLabel();
		addComponent(errorLabel);
		setComponentAlignment(errorLabel, Alignment.TOP_LEFT);

		if (!isProductionMode()) {
			if (cause != null) {
				addComponent(createDetailsButtonLayout());
				addComponent(createExceptionTraceLayout());
			}
		}
	}

	private HorizontalLayout createDetailsButtonLayout() {
		final HorizontalLayout buttonsLayout = new HorizontalLayout();
		final Button errorDetailsButton = new Button("Show error detail", event -> showExceptionTrace());
		errorDetailsButton.addStyleName("link small");
		errorDetailsButton.setIcon(VaadinIcons.PLUS);
		buttonsLayout.addComponent(errorDetailsButton);
		buttonsLayout.setComponentAlignment(errorDetailsButton, Alignment.TOP_LEFT);
		return buttonsLayout;
	}

	private void showExceptionTrace() {
		exceptionTraceLayout.setVisible(!exceptionTraceLayout.isVisible());
	}

	private VerticalLayout createExceptionTraceLayout() {
		exceptionTraceLayout = new VerticalLayout();
		exceptionTraceLayout.addComponent(createStackTraceArea());
		exceptionTraceLayout.setVisible(false);
		return exceptionTraceLayout;
	}

	protected TextArea createStackTraceArea() {
		final TextArea area = new TextArea();
		area.setWordWrap(false);
		area.setWidth(100, Unit.PERCENTAGE);
		area.setRows(15);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintWriter pw = new PrintWriter(baos);
		if (cause instanceof MethodException) {
			cause.getCause().printStackTrace(pw);
		} else {
			cause.printStackTrace(pw);
		}
		pw.flush();
		area.setValue(baos.toString());
		return area;
	}

	protected Label createErrorLabel() {
		String label = errorMessage == null ? DEFAULT_ERROR_LABEL_MESSAGE : errorMessage;
		// if (isProductionMode()) {
		// label = label.concat(String.format("<br />Report the following
		// code:<p><center>%s<center/></p>", uuid));
		// }
		final Label errorLabel = new Label(label, ContentMode.HTML);
		errorLabel.setWidth(100, Unit.PERCENTAGE);
		return errorLabel;
	}

	/**
	 * To know if the app is in production mode.
	 * 
	 * @return true if is production mode
	 */
	private boolean isProductionMode() {
		return VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode();
	}

}
