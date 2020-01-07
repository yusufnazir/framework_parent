package software.simple.solutions.framework.core.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.Command;

public class DetailsWindow extends Dialog {

	private static final long serialVersionUID = -8851440301254569930L;

	private Details details;
	private String summary;
	private TextArea content;

	public static DetailsWindow build(String summary, Exception e) {
		return new DetailsWindow(e);
	}

	public DetailsWindow() {
		super();
	}

	private DetailsWindow(Exception e) {
		this();
		content = new TextArea();
		// content.setWidth("1200px");
		// content.setMaxHeight("400px");

		details = new Details();
		details.setSummaryText(e.getMessage());
		details.addContent(content);
		details.setVisible(false);

		createStackTraceArea(e);

	}

	protected void createStackTraceArea(Exception cause) {
		// errorMessageFld.setWordWrap(false);
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			final PrintWriter pw = new PrintWriter(baos);
			cause.printStackTrace(pw);
			pw.flush();
			UI.getCurrent().access(new Command() {

				private static final long serialVersionUID = 8604085057861768093L;

				@Override
				public void execute() {
					String message = cause.getMessage();
					details.setSummaryText(message);
					details.setVisible(true);
					content.setValue(baos.toString());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
