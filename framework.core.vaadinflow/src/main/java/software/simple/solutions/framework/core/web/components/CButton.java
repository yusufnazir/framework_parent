package software.simple.solutions.framework.core.web.components;

import java.util.Optional;

import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;

import software.simple.solutions.framework.core.util.PropertyResolver;

//@CssImport(value = "styles/button.css", themeFor = "vaadin-button")
public class CButton extends Button {

	private static final long serialVersionUID = 6582368776917351402L;

	private String captionKey;

	public CButton() {
	}

	public CButton(String caption) {
		this();
		super.setText(caption);
	}

	public CButton(Icon icon) {
		super(icon);
	}

	public void setCaptionByKey(String key) {
		captionKey = key;
		super.setText(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	public String getCaptionKey() {
		return captionKey;
	}

	private static final PropertyDescriptor<String, Optional<String>> titleDescriptor = PropertyDescriptors
			.optionalAttributeWithDefault("title", "");

	public void setTitle(String title) {
		set(titleDescriptor, title);
	}

}
