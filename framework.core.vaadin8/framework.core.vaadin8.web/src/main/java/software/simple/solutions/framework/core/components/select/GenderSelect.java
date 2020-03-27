package software.simple.solutions.framework.core.components.select;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.IGenderService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class GenderSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public GenderSelect() {
		IGenderService genderService = ContextProvider.getBean(IGenderService.class);
		List<ComboItem> items;
		try {
			items = genderService.getForListing(Gender.class, true);
			items.stream().forEach(p -> p
					.setName(PropertyResolver.getPropertyValueByLocale(p.getName(), UI.getCurrent().getLocale())));
			setItems(items);
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}

	}

}
