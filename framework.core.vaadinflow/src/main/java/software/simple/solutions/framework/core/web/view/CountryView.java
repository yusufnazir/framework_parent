package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Country;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CountryProperty;
import software.simple.solutions.framework.core.service.facade.CountryServiceFacade;
import software.simple.solutions.framework.core.valueobjects.CountryVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.CountryForm;

@Route(value = Routes.COUNTRY, layout = MainView.class)
public class CountryView extends BasicTemplate<Country> {

	private static final long serialVersionUID = 6503015064562511801L;

	public CountryView() {
		setEntityClass(Country.class);
		setServiceClass(CountryServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(CountryForm.class);
		setParentReferenceKey(ReferenceKey.COUNTRY);
		setEditRoute(Routes.COUNTRY_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Country::getName, CountryProperty.NAME);
		addContainerProperty(Country::getAlpha2, CountryProperty.ALPHA2);
		addContainerProperty(Country::getAlpha3, CountryProperty.ALPHA3);
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
		private CStringIntervalLayout alpha2Fld;
		private CStringIntervalLayout alpha3Fld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, CountryProperty.NAME, 0, 0);
			activeFld = addField(ActiveSelect.class, CountryProperty.ACTIVE, 0, 1);
			alpha2Fld = addField(CStringIntervalLayout.class, CountryProperty.ALPHA2, 1, 0);
			alpha3Fld = addField(CStringIntervalLayout.class, CountryProperty.ALPHA3, 1, 1);
		}

		@Override
		public Object getCriteria() {
			CountryVO vo = new CountryVO();
			vo.setNameInterval(nameFld.getValue());
			vo.setAlpha2Interval(alpha2Fld.getValue());
			vo.setAlpha3Interval(alpha3Fld.getValue());
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
