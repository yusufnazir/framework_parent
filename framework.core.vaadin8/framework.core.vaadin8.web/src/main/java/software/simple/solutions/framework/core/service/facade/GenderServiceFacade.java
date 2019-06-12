package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.IGenderService;
import software.simple.solutions.framework.core.service.ISuperService;

public class GenderServiceFacade extends SuperServiceFacade<IGenderService> implements IGenderService {

	public static final long serialVersionUID = 3660929213971273211L;

	public GenderServiceFacade(UI ui, Class<IGenderService> s) {
		super(ui, s);
	}

	public static GenderServiceFacade get(UI ui) {
		return new GenderServiceFacade(ui, IGenderService.class);
	}

}
