package software.simple.solutions.framework.core.components;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.data.provider.Query;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CTwinColSelect extends TwinColSelect<ComboItem> implements IField {

	private static final long serialVersionUID = -4573724116863038490L;

	public CTwinColSelect() {
		setItemCaptionGenerator(new ItemCaptionGenerator<ComboItem>() {

			private static final long serialVersionUID = 4852466566630532637L;

			@Override
			public String apply(ComboItem item) {
				return item.getCaption();
			}
		});
	}

	public void setLongValues(Set<Long> values) {
		if (values != null) {
			Set<ComboItem> toBeSet = new HashSet<ComboItem>();
			for (Long value : values) {
				Optional<ComboItem> optional = getDataProvider().fetch(new Query<>()).collect(Collectors.toList())
						.stream().filter(p -> ((Comparable) p.getId()).compareTo((Comparable) value) == 0).findFirst();
				if (optional.isPresent()) {
					toBeSet.add(optional.get());
				}
			}
			setValue(toBeSet);
		}
	}

	@Override
	public void setCaptionByKey(String key) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public boolean isThisRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequired() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
