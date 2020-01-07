package software.simple.solutions.framework.core.components.select;

import java.util.List;

import software.simple.solutions.framework.core.entities.Country;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.ICountryService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.web.components.CComboBox;

public class CountrySelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public CountrySelect() throws FrameworkException {
		ICountryService countryService = ContextProvider.getBean(ICountryService.class);
		List<ComboItem> items = countryService.getForListing(Country.class, true);
		setItems(items);
	}

}
