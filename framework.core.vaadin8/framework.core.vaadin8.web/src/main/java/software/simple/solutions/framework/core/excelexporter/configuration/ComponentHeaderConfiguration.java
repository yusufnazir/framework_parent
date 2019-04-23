/*
 * 
 */
package software.simple.solutions.framework.core.excelexporter.configuration;

import com.vaadin.ui.components.grid.HeaderRow;

/**
 * The Class ComponentHeaderConfiguration is used to configure footers for the
 * Excel component
 *
 * @author Kartik Suba
 */
public class ComponentHeaderConfiguration extends AbstractComponentHeaderFooterConfiguration<HeaderRow> {

	private boolean autoFilter = false;

	public boolean isAutoFilter() {
		return this.autoFilter;
	}

	public void setAutoFilter(boolean autoFilter) {
		this.autoFilter = autoFilter;
	}

}