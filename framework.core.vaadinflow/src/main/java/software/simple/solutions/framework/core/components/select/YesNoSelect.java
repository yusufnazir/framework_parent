package software.simple.solutions.framework.core.components.select;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.components.CComboBox;

public class YesNoSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;
	public static final Boolean ACTIVE = true;
	public static final Boolean IN_ACTIVE = false;

	public YesNoSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(ACTIVE,
				PropertyResolver.getPropertyValueByLocale("system.item.question.yes", UI.getCurrent().getLocale())));
		comboItems.add(new ComboItem(IN_ACTIVE,
				PropertyResolver.getPropertyValueByLocale("system.item.question.no", UI.getCurrent().getLocale())));
		setItems(comboItems);
	}

}
