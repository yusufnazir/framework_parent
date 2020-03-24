package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ViewProperty;
import software.simple.solutions.framework.core.service.facade.ViewServiceFacade;
import software.simple.solutions.framework.core.valueobjects.ViewVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.ViewForm;

@Route(value = Routes.VIEW, layout = MainView.class)
public class ViewView extends BasicTemplate<View> {

	private static final long serialVersionUID = 6503015064562511801L;

	public ViewView() {
		setEntityClass(View.class);
		setServiceClass(ViewServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(ViewForm.class);
		setParentReferenceKey(ReferenceKey.VIEW);
		setEditRoute(Routes.VIEW_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(View::getCode, ViewProperty.CODE);
		addContainerProperty(View::getName, ViewProperty.NAME);
		addContainerProperty(View::getViewClassName, ViewProperty.CLASS_NAME);
	}

	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private CStringIntervalLayout classNameFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, ViewProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, ViewProperty.NAME, 0, 1);
			classNameFld = addField(CStringIntervalLayout.class, ViewProperty.CLASS_NAME, 1, 0);
			activeFld = addField(ActiveSelect.class, ViewProperty.ACTIVE, 1, 1);
		}

		@Override
		public Object getCriteria() {
			ViewVO vo = new ViewVO();
			vo.setActive(activeFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setViewClassNameInterval(classNameFld.getValue());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
}
