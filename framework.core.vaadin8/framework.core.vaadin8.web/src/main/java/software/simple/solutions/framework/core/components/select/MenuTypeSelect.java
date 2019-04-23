package software.simple.solutions.framework.core.components.select;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.MenuProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class MenuTypeSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public MenuTypeSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(MenuType.HEAD_MENU, PropertyResolver
				.getPropertyValueByLocale(MenuProperty.TYPE + "." + MenuType.HEAD_MENU, UI.getCurrent().getLocale())));
		comboItems.add(new ComboItem(MenuType.CHILD, PropertyResolver
				.getPropertyValueByLocale(MenuProperty.TYPE + "." + MenuType.CHILD, UI.getCurrent().getLocale())));
		comboItems.add(new ComboItem(MenuType.TAB, PropertyResolver
				.getPropertyValueByLocale(MenuProperty.TYPE + "." + MenuType.TAB, UI.getCurrent().getLocale())));
		comboItems.add(new ComboItem(MenuType.LOOKUP, PropertyResolver
				.getPropertyValueByLocale(MenuProperty.TYPE + "." + MenuType.LOOKUP, UI.getCurrent().getLocale())));
		setItems(comboItems);
	}

}
