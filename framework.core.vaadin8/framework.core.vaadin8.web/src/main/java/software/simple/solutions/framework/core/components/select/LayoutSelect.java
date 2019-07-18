package software.simple.solutions.framework.core.components.select;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.constants.LayoutType;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class LayoutSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public LayoutSelect() {
		List<ComboItem> comboItems = new ArrayList<ComboItem>();
		comboItems.add(new ComboItem(LayoutType.TOP_MENU, PropertyResolver
				.getPropertyValueByLocale(SystemProperty.SYSTEM_LAYOUT_TYPE_TOP_MENU, UI.getCurrent().getLocale())));
		comboItems.add(new ComboItem(LayoutType.APP_LAYOUT, PropertyResolver
				.getPropertyValueByLocale(SystemProperty.SYSTEM_LAYOUT_TYPE_APP_LAYOUT, UI.getCurrent().getLocale())));
		setItems(comboItems);
	}

}
