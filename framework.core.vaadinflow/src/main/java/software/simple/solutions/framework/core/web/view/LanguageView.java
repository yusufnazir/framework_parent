package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.LanguageProperty;
import software.simple.solutions.framework.core.service.facade.LanguageServiceFacade;
import software.simple.solutions.framework.core.valueobjects.LanguageVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.LanguageForm;

@Route(value = Routes.LANGUAGE, layout = MainView.class)
public class LanguageView extends BasicTemplate<Language> {

	private static final long serialVersionUID = 6503015064562511801L;

	public LanguageView() {
		setEntityClass(Language.class);
		setServiceClass(LanguageServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(LanguageForm.class);
		setParentReferenceKey(ReferenceKey.LANGUAGE);
		setEditRoute(Routes.LANGUAGE_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Language::getCode, LanguageProperty.CODE);
		addContainerProperty(Language::getName, LanguageProperty.NAME);
		addContainerProperty(Language::getDescription, LanguageProperty.DESCRIPTION);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout descriptionFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, LanguageProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, LanguageProperty.NAME, 0, 1);
			descriptionFld = addField(CStringIntervalLayout.class, LanguageProperty.DESCRIPTION, 1, 0);
			activeFld = addField(ActiveSelect.class, LanguageProperty.ACTIVE, 1, 1);
		}

		@Override
		public Object getCriteria() {
			LanguageVO vo = new LanguageVO();
			vo.setActive(activeFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
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
