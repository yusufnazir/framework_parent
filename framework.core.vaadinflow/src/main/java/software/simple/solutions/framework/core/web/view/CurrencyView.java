package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Currency;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CurrencyProperty;
import software.simple.solutions.framework.core.service.facade.CurrencyServiceFacade;
import software.simple.solutions.framework.core.valueobjects.CurrencyVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.CurrencyForm;

@Route(value = Routes.CURRENCY, layout = MainView.class)
public class CurrencyView extends BasicTemplate<Currency> {

	private static final long serialVersionUID = 6503015064562511801L;

	public CurrencyView() {
		setEntityClass(Currency.class);
		setServiceClass(CurrencyServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(CurrencyForm.class);
		setParentReferenceKey(ReferenceKey.CURRENCY);
		setEditRoute(Routes.CURRENCY_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Currency::getCode, CurrencyProperty.CODE);
		addContainerProperty(Currency::getName, CurrencyProperty.NAME);
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

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, CurrencyProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, CurrencyProperty.NAME, 1, 0);
		}

		@Override
		public Object getCriteria() {
			CurrencyVO vo = new CurrencyVO();
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
