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
import com.vaadin.event.ShortcutAction.KeyCode;
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
import com.vaadin.ui.Window;

import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.Arg.Key;
import software.simple.solutions.framework.core.exceptions.Arg.Value;
import software.simple.solutions.framework.core.exceptions.ExceptionHolder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class MessageWindowHandler {

	private static final Logger logger = LoggerFactory.getLogger(MessageWindowHandler.class);

	public MessageWindowHandler(final String messageProperty, Arg args) {
		new MessageWindow(messageProperty, args).open(UI.getCurrent());
	}

	public MessageWindowHandler(final Throwable cause) {
		new MessageWindow(cause).open(UI.getCurrent());
	}

	public class MessageWindow extends Window {

		private static final String DEFAULT_CAPTION = "<b>An error has occurred</b>";

		private static final String DEFAULT_ERROR_LABEL_MESSAGE = "Please contact the system administrator for more information.";

		private VerticalLayout exceptionTraceLayout;

		private Throwable cause;

		private String errorMessage;

		private String messageProperty;

		private Arg args;

		/**
		 * Constructs an ErrorWindow component with default settings.
		 */
		public MessageWindow(final String messageProperty, Arg args) {
			super();
			this.messageProperty = messageProperty;
			this.args = args;
			initWindow();
		}

		/**
		 * Constructs an ErrorWindow with an specific error message.
		 */
		public MessageWindow(final Throwable cause) {
			super();
			this.cause = cause;
			initWindow();
		}

		public MessageWindow open(final UI ui) {
			ui.addWindow(this);
			return this;
		}

		private void initWindow() {
			setWidth(800, Unit.PIXELS);
			setModal(true);
			addCloseShortcut(KeyCode.ESCAPE);
			setCaption(DEFAULT_CAPTION);
			setCaptionAsHtml(true);
			setContent(createMainLayout());
		}

		/**
		 * Creates the main layout of the ErrorWindow.
		 */
		private VerticalLayout createMainLayout() {
			if (cause != null) {
				ExceptionHolder exceptionHolder = null;
				if (cause instanceof FrameworkException) {
					// exceptionHolder = ((FrameworkException)
					// cause).getExceptionHolder();
				}

				if (exceptionHolder != null) {
					messageProperty = exceptionHolder.getMessageKey();
					args = exceptionHolder.getArgs();
				}
			}

			if (StringUtils.isNotBlank(messageProperty)) {
				// IMessagePerLocaleService messagePerLocaleService =
				// ContextProvider
				// .getBean(IMessagePerLocaleService.class);
				// String iso3Language =
				// UI.getCurrent().getLocale().getISO3Language();
				// MessagePerLocale messagePerLocale = null;
				// try {
				// messagePerLocale =
				// messagePerLocaleService.getByMessageAndLocale(messageProperty,
				// iso3Language);
				// errorMessage = messagePerLocale.getReason();
				// } catch (FrameworkException e) {
				// logger.error(e.getMessage(), e);
				// }
				// if (messagePerLocale == null) {
				// logger.error("No messagenumber defined for [{0}]",
				// messageProperty, null);
				// }

				errorMessage = PropertyResolver.getPropertyValueByLocale(messageProperty, UI.getCurrent().getLocale(),
						args);
				if (errorMessage == null) {
					errorMessage = "No message defined";
				} else if (args != null) {
					MessageFormat messageFormat = new MessageFormat(errorMessage);
					List<Value> values = args.getValues();
					List<Object> arguments = new ArrayList<>();
					for (Value value : values) {
						if (value instanceof Key) {
							String valueByLocale = PropertyResolver.getPropertyValueByLocale(value.getValue(),
									UI.getCurrent().getLocale());
							arguments.add(valueByLocale);
						} else {
							arguments.add(value.getValue());
						}
					}
					errorMessage = messageFormat.format(arguments.toArray());
				}
			}

			final VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSpacing(false);
			mainLayout.setMargin(true);

			final Label errorLabel = createErrorLabel();
			mainLayout.addComponent(errorLabel);
			mainLayout.setComponentAlignment(errorLabel, Alignment.TOP_LEFT);

			if (!isProductionMode() && cause != null) {
				mainLayout.addComponent(createDetailsButtonLayout());
				mainLayout.addComponent(createExceptionTraceLayout());
			}

			final Button closeButton = new Button("Close", event -> close());
			mainLayout.addComponent(closeButton);
			mainLayout.setComponentAlignment(closeButton, Alignment.TOP_RIGHT);

			return mainLayout;
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
			setModal(Boolean.TRUE);
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

}
