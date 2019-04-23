package software.simple.solutions.framework.core.components;

import org.apache.commons.text.WordUtils;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CaptionLabel extends Label {
	private static final long serialVersionUID = 6143460537630112099L;
	private String key;

	public CaptionLabel() {
		addStyleName(Style.LABEL_CAPTION_MODE);
		setProperties();
	}

	public CaptionLabel(String value) {
		this();
		setValueByKey(value);
		setProperties();
	}

	public void setProperties() {

	}

	/**
	 * Set's the value based on the locale using the key sent.
	 * 
	 * @param key
	 */
	public void setValueByKey(String key) {
		this.key = key;
		setValue(WordUtils
				.capitalize(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale())));
//		setDescription("KEY [" + key + "]");
	}

	/**
	 * Set's the value without using a key.
	 * 
	 * @param value
	 */
	public void setValueWithoutKey(String value) {
		setValue(value);
	}

	/**
	 * Get the key used for the setting the value.
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

}
