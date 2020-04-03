package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Nationality;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CountryProperty;
import software.simple.solutions.framework.core.properties.NationalityProperty;
import software.simple.solutions.framework.core.service.facade.NationalityServiceFacade;
import software.simple.solutions.framework.core.valueobjects.NationalityVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.NationalityForm;

@Route(value = Routes.NATIONALITY, layout = MainView.class)
public class NationalityView extends BasicTemplate<Nationality> {

	private static final long serialVersionUID = 6503015064562511801L;

	public NationalityView() {
		setEntityClass(Nationality.class);
		setServiceClass(NationalityServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(NationalityForm.class);
		setParentReferenceKey(ReferenceKey.NATIONALITY);
		setEditRoute(Routes.NATIONALITY_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Nationality::getName, CountryProperty.NAME);
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
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, NationalityProperty.NAME, 0, 0);
			activeFld = addField(ActiveSelect.class, NationalityProperty.ACTIVE, 0, 1);
		}

		@Override
		public Object getCriteria() {
			NationalityVO vo = new NationalityVO();
			vo.setNameInterval(nameFld.getValue());
			vo.setActive(activeFld.getItemId());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
