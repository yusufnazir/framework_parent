package software.simple.solutions.framework.core.web.view.forms;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.vaadin.pekka.WysiwygE;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.config.MailTemplateGroup;
import software.simple.solutions.framework.core.constants.MailTemplatePlaceholderItem;
import software.simple.solutions.framework.core.entities.MailTemplate;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.MailTemplateProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.MailTemplateVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.MAIL_TEMPLATE_EDIT, layout = MainView.class)
public class MailTemplateForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField nameFld;
	private CTextArea descriptionFld;

	private CTextField subjectFld;
	private WysiwygE messageFld;

	private Accordion accordion;

	private MailTemplate mailTemplate;

	private HorizontalLayout horizontalLayout;

	public MailTemplateForm() {
		super();
	}

	@Override
	public void executeBuild() {
		createFormGrid();
	}

	private CFormLayout createFormGrid() {
		horizontalLayout = new HorizontalLayout();
		add(horizontalLayout);

		Panel formCard = new Panel();
		formCard.setHeaderKey(SystemProperty.SYSTEM_PANEL_BASIC_INFORMATION);
		horizontalLayout.add(formCard);
		formGrid = new CFormLayout();
		formCard.add(formGrid);
		formCard.setMaxWidth("600px");

		nameFld = formGrid.add(CTextField.class, MailTemplateProperty.NAME);

		descriptionFld = formGrid.add(CTextArea.class, MailTemplateProperty.DESCRIPTION);

		subjectFld = formGrid.add(CTextField.class, MailTemplateProperty.SUBJECT);
		subjectFld.setWidth("800px");

		Label label = formGrid.add(Label.class, MailTemplateProperty.MESSAGE);
		label.setText(
				PropertyResolver.getPropertyValueByLocale(MailTemplateProperty.MESSAGE, UI.getCurrent().getLocale()));
		label.addClassName("my-custom-label");

		messageFld = formGrid.add(WysiwygE.class, MailTemplateProperty.MESSAGE);
		messageFld.setWidth("800px");
		messageFld.setHeight("400px");

		createPlaceholderLayout();

		return formGrid;
	}

	private void createPlaceholderLayout() {
		Panel card = new Panel();
		card.setHeaderKey(MailTemplateProperty.PLACEHOLDERS);
		horizontalLayout.add(card);
		card.setMinWidth("400px");
		accordion = new Accordion();
		accordion.setWidthFull();
		card.add(accordion);
	}

	private void createTabs() {
		Map<String, List<MailTemplatePlaceholderItem>> groupMap = MailTemplateGroup.getItems();
		for (Entry<String, List<MailTemplatePlaceholderItem>> entry : groupMap.entrySet()) {
			VerticalLayout verticalLayout = new VerticalLayout();
			verticalLayout.setWidthFull();
			accordion.add(PropertyResolver.getPropertyValueByLocale(entry.getKey(), UI.getCurrent().getLocale()),
					verticalLayout);

			List<MailTemplatePlaceholderItem> list = entry.getValue();
			for (MailTemplatePlaceholderItem mailTemplatePlaceholderItem : list) {
				VerticalLayout layout = new VerticalLayout();
				layout.setWidthFull();
				verticalLayout.add(layout);
				layout.getStyle().set("border", "solid 1px");
				Label title = new Label(PropertyResolver.getPropertyValueByLocale(mailTemplatePlaceholderItem.getKey(),
						UI.getCurrent().getLocale()));
				title.addClassName("my-custom-label");
				layout.add(title);
				Label label = new Label("${"
						+ mailTemplatePlaceholderItem.getKey().replace(MailTemplateGroup.PREFIX, "").replace(".", "_")
						+ "}");
				layout.add(label);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public MailTemplate setFormValues(Object entity) throws FrameworkException {
		mailTemplate = (MailTemplate) entity;
		nameFld.setValue(mailTemplate.getName());
		descriptionFld.setValue(mailTemplate.getDescription());

		subjectFld.setValue(mailTemplate.getSubject());
		messageFld.setValue(mailTemplate.getMessage());

		createTabs();

		return mailTemplate;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		createTabs();
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		MailTemplateVO vo = new MailTemplateVO();
		vo.setId(mailTemplate == null ? null : mailTemplate.getId());
		vo.setName(nameFld.getValue());
		vo.setDescription(descriptionFld.getValue());
		vo.setSubject(subjectFld.getValue());
		vo.setMessage(messageFld.getValue());

		return vo;
	}

}
