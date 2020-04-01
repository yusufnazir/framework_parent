package software.simple.solutions.framework.core.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPropertyPerLocaleService;
import software.simple.solutions.framework.core.service.IPropertyService;

@Component
public class PropertyHolder {

	@Autowired
	private IPropertyService propertyService;

	@Autowired
	private IPropertyPerLocaleService propertyPerLocaleService;

	public static Map<String, Map<String, Properties>> localization;

	public static Map<String, Properties> propertyLocalized;

	@PostConstruct
	public void init() {
		initForProperty();
		initExcludeProperty();
		localization.put(ReferenceKey.PROPERTY, propertyLocalized);

	}

	private void initExcludeProperty() {
		localization = new HashMap<String, Map<String, Properties>>();
		try {
			List<PropertyPerLocale> propertyPerLocales = propertyPerLocaleService.findAllButProperty();
			for (PropertyPerLocale propertyPerLocale : propertyPerLocales) {

				String referenceKey = propertyPerLocale.getReferenceKey();
				if (!localization.containsKey(referenceKey)) {
					localization.put(referenceKey, new HashMap<String, Properties>());
				}
				Map<String, Properties> mapByReference = localization.get(referenceKey);

				String languageCode = propertyPerLocale.getLanguage().getCode().toLowerCase();

				if (!mapByReference.containsKey(languageCode)) {
					mapByReference.put(languageCode, new Properties());
				}

				String referenceId = propertyPerLocale.getReferenceId();
				Properties properties = mapByReference.get(languageCode);
				properties.setProperty(referenceId, propertyPerLocale.getValue());
			}

		} catch (FrameworkException e) {
			e.printStackTrace();
		}

	}

	private void initForProperty() {
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

	public static void updateKeyValue(String referenceKey, String locale, String referenceId, String value) {
		if (!localization.containsKey(referenceKey)) {
			localization.put(referenceKey, new HashMap<String, Properties>());
		}
		Map<String, Properties> mapByReference = localization.get(referenceKey);
		if (!mapByReference.containsKey(locale.toLowerCase())) {
			mapByReference.put(locale.toLowerCase(), new Properties());
		}
		Properties properties = mapByReference.get(locale.toLowerCase());
		if (properties != null) {
			properties.setProperty(referenceId, value);
		}
	}

	public static void updateKeyValue(String referenceKey, String propertyKey, PropertyPerLocale propertyPerLocale) {
		if (propertyPerLocale != null) {
			String locale = propertyPerLocale.getLanguage().getCode();
			if (locale != null) {
				if (!localization.containsKey(referenceKey)) {
					localization.put(referenceKey, new HashMap<String, Properties>());
				}
				Map<String, Properties> mapByReference = localization.get(referenceKey);

				if (!mapByReference.containsKey(locale.toLowerCase())) {
					mapByReference.put(locale.toLowerCase(), new Properties());
				}

				Properties properties = mapByReference.get(locale.toLowerCase());
				if (properties != null) {
					String value = propertyPerLocale.getValue();
					properties.setProperty(propertyKey, value);
				}
			}
		}
	}

}
