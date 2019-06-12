package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.ILanguageService;
import software.simple.solutions.framework.core.service.ISuperService;

public class LanguageServiceFacade extends SuperServiceFacade<ILanguageService> implements ILanguageService {

	public static final long serialVersionUID = -6044579271502400768L;

	public LanguageServiceFacade(UI ui, Class<ILanguageService> s) {
		super(ui, s);
	}

	public static LanguageServiceFacade get(UI ui) {
		return new LanguageServiceFacade(ui, ILanguageService.class);
	}

}
