package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.ICountryService;

public class CountryServiceFacade extends SuperServiceFacade<ICountryService> implements ICountryService {

	public static final long serialVersionUID = 7118619650939156218L;

	public CountryServiceFacade(UI ui, Class<ICountryService> s) {
		super(ui, s);
	}

	public static CountryServiceFacade get(UI ui) {
		return new CountryServiceFacade(ui, ICountryService.class);
	}
}
