package software.simple.solutions.framework.core.components;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Component;

public class ObjectInitializer implements Serializable {

	private static final long serialVersionUID = -6056629411274762326L;

	private static final Logger logger = LogManager.getLogger(ObjectInitializer.class);

	public Component initializeClass(Class<?> cl) {
		Component instance = null;
		if (cl != null) {
			try {
				instance = (Component) cl.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return instance;
	}

}
