package software.simple.solutions.framework.core.components.select;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ActiveSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;
	public static final Boolean ACTIVE = true;
	public static final Boolean IN_ACTIVE = false;

	public ActiveSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(ACTIVE,
				PropertyResolver.getPropertyValueByLocale("system.item.status.active", UI.getCurrent().getLocale())));
		comboItems.add(new ComboItem(IN_ACTIVE,
				PropertyResolver.getPropertyValueByLocale("system.item.status.inactive", UI.getCurrent().getLocale())));
		setItems(comboItems);
	}

}
