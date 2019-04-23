package software.simple.solutions.framework.core.components;

import java.awt.Component;
import java.util.Map;

public interface ShareableComponents {

	/**
	 * Add a component that needs to be shared with the subviews and/or tabs.
	 * 
	 * @param key
	 * @param component
	 */
	public void addSharedComponents(String key, Component component);

	/**
	 * Retrieves a map of shared components
	 * 
	 * @return
	 */
	public Map<String, Component> getSharedComponents();

	/**
	 * Sets a map of shared components
	 * 
	 * @param sharedComponents
	 */
	public void setSharedComponents(Map<String, Component> sharedComponents);
}
