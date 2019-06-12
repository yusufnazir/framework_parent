package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.ICountryService;
import software.simple.solutions.framework.core.service.ISuperService;

public class CountryServiceFacade extends SuperServiceFacade<ICountryService> implements ICountryService {

	public static final long serialVersionUID = 7118619650939156218L;

	public CountryServiceFacade(UI ui, Class<ICountryService> s) {
		super(ui, s);
	}

	public static CountryServiceFacade get(UI ui) {
		return new CountryServiceFacade(ui, ICountryService.class);
	}
}
