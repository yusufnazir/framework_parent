package software.simple.solutions.framework.core.components.select;

import java.util.List;
import java.util.stream.Collectors;

import software.simple.solutions.framework.core.components.CTwinColSelect;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class PrivilegeTwinColSelect extends CTwinColSelect {

	private static final long serialVersionUID = -4573724116863038490L;

	public void setValues(List<Privilege> values) {
		if (values != null) {
			List<ComboItem> items = values.stream().map(
					p -> new ComboItem(p.getId(), p.getCode(), PropertyResolver.getPropertyValueByLocale(p.getKey())))
					.collect(Collectors.toList());
			setItems(items);
		}
	}

}
