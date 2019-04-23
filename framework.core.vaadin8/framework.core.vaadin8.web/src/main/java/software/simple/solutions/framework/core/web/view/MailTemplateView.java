package software.simple.solutions.framework.core.web.view;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CRichTextArea;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.MailTemplateGroup;
import software.simple.solutions.framework.core.constants.MailTemplatePlaceholderItem;
import software.simple.solutions.framework.core.entities.MailTemplate;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.LanguageProperty;
import software.simple.solutions.framework.core.properties.MailTemplateProperty;
import software.simple.solutions.framework.core.service.IMailTemplateService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.MailTemplateVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class MailTemplateView extends BasicTemplate<MailTemplate> {

	private static final long serialVersionUID = 6503015064562511801L;

	public MailTemplateView() {
		setEntityClass(MailTemplate.class);
		setServiceClass(IMailTemplateService.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
		setSkipActiveColumn(true);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(MailTemplate::getName, MailTemplateProperty.NAME);
		addContainerProperty(MailTemplate::getDescription, MailTemplateProperty.DESCRIPTION);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout descriptionFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, LanguageProperty.NAME, 0, 0);
			descriptionFld = addField(CStringIntervalLayout.class, LanguageProperty.DESCRIPTION, 0, 1);
		}

		@Override
		public Object getCriteria() {
			MailTemplateVO vo = new MailTemplateVO();
			vo.setNameInterval(nameFld.getValue());
			vo.setDescriptionInterval(descriptionFld.getValue());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField nameFld;
		private CTextArea descriptionFld;

		private CTextField subjectFld;
		private CRichTextArea messageFld;

		private Accordion accordion;

		private MailTemplate mailTemplate;

		public Form() {
			super();
		}

		@Override
		public void executeBuild() {
			formGrid = createFormGrid();
			addComponent(formGrid);
		}

		private CGridLayout createFormGrid() {
			formGrid = ComponentUtil.createGrid();

			nameFld = formGrid.addField(CTextField.class, MailTemplateProperty.NAME, 0, 0);

			descriptionFld = formGrid.addField(CTextArea.class, MailTemplateProperty.DESCRIPTION, 0, 1, 1, 2);
			descriptionFld.setRows(2);

			subjectFld = formGrid.addField(CTextField.class, MailTemplateProperty.SUBJECT, 0, 3, 3, 3);
			subjectFld.setWidth("800px");

			messageFld = formGrid.addField(CRichTextArea.class, MailTemplateProperty.MESSAGE, 0, 4, 3, 6);
			messageFld.setWidth("800px");
			messageFld.setHeight("400px");

			accordion = formGrid.addField(Accordion.class, 4, 3, 4, 6);

			return formGrid;
		}

		private void createTabs() {
			Map<String, List<MailTemplatePlaceholderItem>> groupMap = new MailTemplateGroup().getItems();
			for (Entry<String, List<MailTemplatePlaceholderItem>> entry : groupMap.entrySet()) {
				VerticalLayout verticalLayout = new VerticalLayout();
				accordion.addTab(verticalLayout);
				accordion.getTab(verticalLayout).setCaption(PropertyResolver.getPropertyValueByLocale(entry.getKey()));

				List<MailTemplatePlaceholderItem> list = entry.getValue();
				for (MailTemplatePlaceholderItem mailTemplatePlaceholderItem : list) {
					Label label = new Label("${" + mailTemplatePlaceholderItem.getKey()
							.replace(MailTemplateGroup.PREFIX, "").replace(".", "_") + "}");
					label.addStyleName(ValoTheme.LABEL_COLORED);
					label.setCaption(PropertyResolver.getPropertyValueByLocale(mailTemplatePlaceholderItem.getKey()));
					verticalLayout.addComponent(label);
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

}
