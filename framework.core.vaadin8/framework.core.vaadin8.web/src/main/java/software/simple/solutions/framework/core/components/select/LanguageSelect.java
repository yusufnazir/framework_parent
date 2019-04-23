package software.simple.solutions.framework.core.components.select;

import java.util.List;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.ILanguageService;
import software.simple.solutions.framework.core.util.ContextProvider;

public class LanguageSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public LanguageSelect() throws FrameworkException {
		ILanguageService languageService = ContextProvider.getBean(ILanguageService.class);
		List<ComboItem> items = languageService.getForListing(Language.class, true);
		setItems(items);
	}

}
