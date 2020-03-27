package software.simple.solutions.framework.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPropertyService;

@Component
public class PropertyHolder {

	@Autowired
	private IPropertyService propertyService;

	public static Map<String, Properties> propertyLocalized;

	@PostConstruct
	public void init() {
		try {
			propertyLocalized = new HashMap<String, Properties>();
			Map<Property, Map<String, PropertyPerLocale>> propertyPerLocales = propertyService.findAll();
			for (Entry<Property, Map<String, PropertyPerLocale>> entry : propertyPerLocales.entrySet()) {
				Property property = entry.getKey();
				Map<String, PropertyPerLocale> values = entry.getValue();
				for (Entry<String, PropertyPerLocale> ppl : values.entrySet()) {
					String languageCode = ppl.getKey().toLowerCase();
					PropertyPerLocale propertyPerLocale = ppl.getValue();
					if (!propertyLocalized.containsKey(languageCode)) {
						propertyLocalized.put(languageCode, new Properties());
					}
					Properties properties = propertyLocalized.get(languageCode);
					properties.setProperty(property.getKey(), propertyPerLocale.getValue());
				}
			}
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}

	public static void updateKeyValue(String locale, String key, String value) {
		Properties properties = propertyLocalized.get(locale);
		if (properties != null) {
			properties.setProperty(key, value);
		}
	}

	public static void updateKeyValue(String propertyKey, PropertyPerLocale propertyPerLocale) {
		if (propertyPerLocale != null) {
			String locale = propertyPerLocale.getLanguage().getCode();

			if (locale != null) {
				Properties properties = propertyLocalized.get(locale.toLowerCase());
				if (properties != null) {
					String value = propertyPerLocale.getValue();
					properties.setProperty(propertyKey, value);
				}
			}
		}
	}

}
