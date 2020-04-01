package software.simple.solutions.framework.core.web.components;

import org.apache.commons.lang3.ObjectUtils;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.textfield.TextField;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class CTextField extends TextField implements Comparable<CTextField> {

	private static final long serialVersionUID = 1456255778562075546L;

	public CTextField() {
		super();
	}
	
	@Override
	public void setValue(String value) {
		if(value==null){
			super.setValue("");
		}else{
			super.setValue(value);
		}
			
	}

	public void setCaptionByKey(String key) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public int compareTo(CTextField o) {
		return ObjectUtils.compare(getValue(), o.getValue());
	}

	@Override
	public void clear() {
		super.clear();
	}

}
