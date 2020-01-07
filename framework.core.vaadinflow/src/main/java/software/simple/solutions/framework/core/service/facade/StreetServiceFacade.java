package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IStreetService;

public class StreetServiceFacade extends SuperServiceFacade<IStreetService> implements IStreetService {

	public static final long serialVersionUID = -6162936333533651571L;

	public StreetServiceFacade(UI ui, Class<IStreetService> s) {
		super(ui, s);
	}

	public static StreetServiceFacade get(UI ui) {
		return new StreetServiceFacade(ui, IStreetService.class);
	}

}
