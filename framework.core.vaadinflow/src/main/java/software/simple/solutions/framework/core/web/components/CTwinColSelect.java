package software.simple.solutions.framework.core.web.components;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.vaadin.tatu.TwinColSelect;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.Query;

import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;

public class CTwinColSelect extends TwinColSelect<ComboItem> implements IField {

	private static final long serialVersionUID = -4573724116863038490L;

	public CTwinColSelect() {
		super();
		setItemLabelGenerator(new ItemLabelGenerator<ComboItem>() {

			private static final long serialVersionUID = 7135232946883364366L;

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
		setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
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
