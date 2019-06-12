package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.ICurrencyService;
import software.simple.solutions.framework.core.service.ISuperService;

public class CurrencyServiceFacade extends SuperServiceFacade<ICurrencyService> implements ICurrencyService {

	public static final long serialVersionUID = 2766768839919302979L;

	public CurrencyServiceFacade(UI ui, Class<ICurrencyService> s) {
		super(ui, s);
	}

	public static CurrencyServiceFacade get(UI ui) {
		return new CurrencyServiceFacade(ui, ICurrencyService.class);
	}

}
