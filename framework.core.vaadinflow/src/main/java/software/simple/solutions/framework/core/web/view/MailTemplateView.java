package software.simple.solutions.framework.core.web.view;

import java.util.Locale;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.MailTemplateReference;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.MailTemplate;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.LanguageProperty;
import software.simple.solutions.framework.core.properties.MailTemplateProperty;
import software.simple.solutions.framework.core.service.facade.MailTemplateServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.MailTemplateVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.MailTemplateForm;

@Route(value = Routes.MAIL_TEMPLATE, layout = MainView.class)
public class MailTemplateView extends BasicTemplate<MailTemplate> {

	private static final long serialVersionUID = 6503015064562511801L;

	public MailTemplateView() {
		setEntityClass(MailTemplate.class);
		setServiceClass(MailTemplateServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(MailTemplateForm.class);
		setParentReferenceKey(ReferenceKey.MAIL_TEMPLATE);
		setEditRoute(Routes.MAIL_TEMPLATE_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(new ValueProvider<MailTemplate, String>() {

			@Override
			public String apply(MailTemplate mailTemplate) {
				String iso3Language = UI.getCurrent().getLocale().getISO3Language();
				return PropertyResolver.getPropertyValueByLocale(ReferenceKey.MAIL_TEMPLATE,
						MailTemplateReference.NAME + mailTemplate.getId(), new Locale(iso3Language), null,
						mailTemplate.getName());
			}
		}, MailTemplateProperty.NAME);
		addContainerProperty(new ValueProvider<MailTemplate, String>() {

			@Override
			public String apply(MailTemplate mailTemplate) {
				String iso3Language = UI.getCurrent().getLocale().getISO3Language();
				return PropertyResolver.getPropertyValueByLocale(ReferenceKey.MAIL_TEMPLATE,
						MailTemplateReference.DESCRIPTION + mailTemplate.getId(), new Locale(iso3Language), null,
						mailTemplate.getName());
			}
		}, MailTemplateProperty.DESCRIPTION);
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

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
