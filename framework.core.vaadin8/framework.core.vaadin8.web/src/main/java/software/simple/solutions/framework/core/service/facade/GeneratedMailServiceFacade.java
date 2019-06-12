package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.IGeneratedMailService;
import software.simple.solutions.framework.core.service.ISuperService;

public class GeneratedMailServiceFacade extends SuperServiceFacade<IGeneratedMailService>
		implements IGeneratedMailService {

	public static final long serialVersionUID = 4504631108907689431L;

	public GeneratedMailServiceFacade(UI ui, Class<IGeneratedMailService> s) {
		super(ui, s);
	}

	public static GeneratedMailServiceFacade get(UI ui) {
		return new GeneratedMailServiceFacade(ui, IGeneratedMailService.class);
	}

}
