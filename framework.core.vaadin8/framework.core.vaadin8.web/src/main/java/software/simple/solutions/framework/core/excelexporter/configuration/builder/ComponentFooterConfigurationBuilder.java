package software.simple.solutions.framework.core.excelexporter.configuration.builder;

import java.util.List;

import com.vaadin.ui.components.grid.FooterRow;

import software.simple.solutions.framework.core.excelexporter.configuration.ComponentFooterConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.MergedCell;

public class ComponentFooterConfigurationBuilder implements Cloneable {
	protected ComponentFooterConfigurationBuilder self;
	protected List<MergedCell> value$mergedCells$java$util$List;
	protected boolean isSet$mergedCells$java$util$List = false;
	protected FooterRow value$row$com$vaadin$ui$components$grid$FooterRow;
	protected boolean isSet$row$com$vaadin$ui$components$grid$FooterRow = false;
	protected String[] value$columnKeys$java$lang$String$;
	protected boolean isSet$columnKeys$java$lang$String$ = false;
	protected Boolean value$defaultConfig$java$lang$Boolean;
	protected boolean isSet$defaultConfig$java$lang$Boolean = false;

	public ComponentFooterConfigurationBuilder() {
		this.self = this;
	}

	public ComponentFooterConfigurationBuilder withMergedCells(List<MergedCell> value) {
		this.value$mergedCells$java$util$List = value;
		this.isSet$mergedCells$java$util$List = true;
		return this.self;
	}

	public ComponentFooterConfigurationBuilder withRow(FooterRow value) {
		this.value$row$com$vaadin$ui$components$grid$FooterRow = value;
		this.isSet$row$com$vaadin$ui$components$grid$FooterRow = true;
		return this.self;
	}

	public ComponentFooterConfigurationBuilder withColumnKeys(String[] value) {
		this.value$columnKeys$java$lang$String$ = value;
		this.isSet$columnKeys$java$lang$String$ = true;
		return this.self;
	}

	public ComponentFooterConfigurationBuilder withDefaultConfig(Boolean value) {
		this.value$defaultConfig$java$lang$Boolean = value;
		this.isSet$defaultConfig$java$lang$Boolean = true;
		return this.self;
	}

	public Object clone() {
		try {
			ComponentFooterConfigurationBuilder result = (ComponentFooterConfigurationBuilder) super.clone();
			result.self = result;
			return result;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	public ComponentFooterConfigurationBuilder but() {
		return (ComponentFooterConfigurationBuilder) clone();
	}

	public ComponentFooterConfiguration build() {
		ComponentFooterConfiguration result = new ComponentFooterConfiguration();
		if (this.isSet$mergedCells$java$util$List) {
			result.setMergedCells(this.value$mergedCells$java$util$List);
		}
		if (this.isSet$row$com$vaadin$ui$components$grid$FooterRow) {
			result.setRow(this.value$row$com$vaadin$ui$components$grid$FooterRow);
		}
		if (this.isSet$columnKeys$java$lang$String$) {
			result.setColumnKeys(this.value$columnKeys$java$lang$String$);
		}
		if (this.isSet$defaultConfig$java$lang$Boolean) {
			result.setDefaultConfig(this.value$defaultConfig$java$lang$Boolean);
		}
		return result;
	}
}
