package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPropertyPerLocaleService;
import software.simple.solutions.framework.core.service.ISuperService;

public class PropertyPerLocaleServiceFacade extends SuperServiceFacade<IPropertyPerLocaleService>
		implements IPropertyPerLocaleService {

	public static final long serialVersionUID = -4089500106852879181L;

	public PropertyPerLocaleServiceFacade(UI ui, Class<IPropertyPerLocaleService> s) {
		super(ui, s);
	}

	public static PropertyPerLocaleServiceFacade get(UI ui) {
		return new PropertyPerLocaleServiceFacade(ui, IPropertyPerLocaleService.class);
	}

	@Override
	public List<PropertyPerLocale> findAll() throws FrameworkException {
		return service.findAll();
	}
}
