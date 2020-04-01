package software.simple.solutions.framework.core.components.select;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.data.provider.Query;

import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.ILanguageService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.web.components.CComboBox;

public class LanguageSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public LanguageSelect() throws FrameworkException {
		ILanguageService languageService = ContextProvider.getBean(ILanguageService.class);
		List<ComboItem> items = languageService.getForListing(Language.class, true);
		setItems(items);
	}
	
	public void setValue(String type) {
		if (type != null) {
			Optional<ComboItem> optional = getDataProvider().fetch(new Query<>()).collect(Collectors.toList()).stream()
					.filter(p -> p.getCode().equalsIgnoreCase(type)).findFirst();
			if (optional.isPresent()) {
				setValue(optional.get());
			}
		}
	}

}
