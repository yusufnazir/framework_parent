package software.simple.solutions.framework.core.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPropertyPerLocaleService;

@Component
public class PropertyHolder {

	@Autowired
	private IPropertyPerLocaleService propertyPerLocaleService;

	public static Map<String, Properties> propertyLocalized;

	@PostConstruct
	public void init() {
		try {
			propertyLocalized = new HashMap<String, Properties>();
			List<PropertyPerLocale> propertyPerLocales = propertyPerLocaleService.findAll();
			Map<String, List<PropertyPerLocale>> perLocale = propertyPerLocales.parallelStream()
					.collect(Collectors.groupingBy(p -> p.getLanguage().getCode().toLowerCase()));
			for (Entry<String, List<PropertyPerLocale>> entry : perLocale.entrySet()) {
				Properties properties = new Properties();
//				entry.getValue().stream().forEach(p -> System.out.println(p.getProperty() == null ? p.getId() : null));
				entry.getValue().parallelStream()
						.forEach(p -> {
							if(p.getProperty()!=null){
								properties.setProperty(p.getProperty().getKey(), p.getValue());
							}
						});
				propertyLocalized.put(entry.getKey(), properties);
			}
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
	}

}
