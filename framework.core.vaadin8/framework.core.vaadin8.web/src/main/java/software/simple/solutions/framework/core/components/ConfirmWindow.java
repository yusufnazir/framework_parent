package software.simple.solutions.framework.core.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ConfirmWindow {

	private static final Logger logger = LogManager.getLogger(ConfirmWindow.class);

	private String titleKey;
	private String messageKey;
	private String yesKey;
	private String noKey;

	public ConfirmWindow(String titleKey, String messageKey, String yesKey, String noKey) {
		this.titleKey = titleKey;
		this.messageKey = messageKey;
		this.yesKey = yesKey;
		this.noKey = noKey;
	}

	public void execute(ConfirmationHandler confirmationHandler) {
		ConfirmDialog confirmDialog = ConfirmDialog.show(UI.getCurrent(),
				PropertyResolver.getPropertyValueByLocale(titleKey, UI.getCurrent().getLocale()),
				PropertyResolver.getPropertyValueByLocale(messageKey, UI.getCurrent().getLocale()),
				PropertyResolver.getPropertyValueByLocale(yesKey, UI.getCurrent().getLocale()),
				PropertyResolver.getPropertyValueByLocale(noKey, UI.getCurrent().getLocale()),
				new ConfirmDialog.Listener() {

					private static final long serialVersionUID = -295041324649782933L;

					@Override
					public void onClose(ConfirmDialog dialog) {
						if (confirmationHandler != null) {
							if (dialog.isConfirmed()) {
								try {
									confirmationHandler.handlePositive();
								} catch (FrameworkException e) {
									logger.error(e.getMessage(), e);
									new MessageWindowHandler(e);
								}
							} else {
								confirmationHandler.handleNegative();
							}
						}
					}
				});
		confirmDialog.setContentMode(ConfirmDialog.ContentMode.HTML);
		confirmDialog.getCancelButton().addStyleName(ValoTheme.BUTTON_FRIENDLY);
		confirmDialog.getCancelButton().setIcon(FontAwesome.BAN);
		
		confirmDialog.getOkButton().addStyleName(ValoTheme.BUTTON_QUIET);
		confirmDialog.getOkButton().addStyleName(ValoTheme.BUTTON_DANGER);
		confirmDialog.getOkButton().addStyleName(Style.BUTTON_DANGEROUS);
		confirmDialog.getOkButton().setIcon(FontAwesome.CHECK);
		confirmDialog.setResizable(true);
		confirmDialog.getContent().setSizeUndefined();
	}

	public interface ConfirmationHandler {

		public void handlePositive() throws FrameworkException;

		public void handleNegative();
	}

}
