package software.simple.solutions.framework.core.excelexporter.configuration.builder;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vaadin.ui.Grid;

import software.simple.solutions.framework.core.excelexporter.configuration.ComponentFooterConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ComponentHeaderConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelComponentConfiguration;
import software.simple.solutions.framework.core.excelexporter.formatter.ColumnFormatter;
import software.simple.solutions.framework.core.excelexporter.function.DataCellStyleGeneratorFunction;

public class ExportExcelComponentConfigurationBuilder<BEANTYPE> implements Cloneable {
	protected ExportExcelComponentConfigurationBuilder<BEANTYPE> self;
	protected BiFunction<XSSFWorkbook, String, XSSFCellStyle> value$headerStyleFunction$java$util$function$BiFunction;
	protected boolean isSet$headerStyleFunction$java$util$function$BiFunction = false;
	protected String[] value$visibleProperties$java$lang$String$;
	protected boolean isSet$visibleProperties$java$lang$String$ = false;
	protected BiFunction<XSSFWorkbook, String, XSSFCellStyle> value$footerStyleFunction$java$util$function$BiFunction;
	protected boolean isSet$footerStyleFunction$java$util$function$BiFunction = false;
	protected DataCellStyleGeneratorFunction value$contentStyleFunction$org$vaadin$addons$excelexporter$function$DataCellStyleGeneratorFunction;
	protected boolean isSet$contentStyleFunction$org$vaadin$addons$excelexporter$function$DataCellStyleGeneratorFunction = false;
	protected List<String> value$integerFormattingProperties$java$util$List;
	protected boolean isSet$integerFormattingProperties$java$util$List = false;
	protected List<String> value$floatFormattingProperties$java$util$List;
	protected boolean isSet$floatFormattingProperties$java$util$List = false;
	protected Map<Object, ColumnFormatter> value$columnFormatters$java$util$Map;
	protected boolean isSet$columnFormatters$java$util$Map = false;
	protected List<String> value$booleanFormattingProperties$java$util$List;
	protected boolean isSet$booleanFormattingProperties$java$util$List = false;
	protected Grid<BEANTYPE> value$grid$com$vaadin$ui$Grid;
	protected boolean isSet$grid$com$vaadin$ui$Grid = false;
	protected List<ComponentFooterConfiguration> value$footerConfigs$java$util$List;
	protected boolean isSet$footerConfigs$java$util$List = false;
	protected List<String> value$dateFormattingProperties$java$util$List;
	protected boolean isSet$dateFormattingProperties$java$util$List = false;
	protected List<ComponentHeaderConfiguration> value$headerConfigs$java$util$List;
	protected boolean isSet$headerConfigs$java$util$List = false;

	public ExportExcelComponentConfigurationBuilder() {
		this.self = this;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withHeaderStyleFunction(
			BiFunction<XSSFWorkbook, String, XSSFCellStyle> value) {
		this.value$headerStyleFunction$java$util$function$BiFunction = value;
		this.isSet$headerStyleFunction$java$util$function$BiFunction = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withVisibleProperties(String[] value) {
		this.value$visibleProperties$java$lang$String$ = value;
		this.isSet$visibleProperties$java$lang$String$ = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withFooterStyleFunction(
			BiFunction<XSSFWorkbook, String, XSSFCellStyle> value) {
		this.value$footerStyleFunction$java$util$function$BiFunction = value;
		this.isSet$footerStyleFunction$java$util$function$BiFunction = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withContentStyleFunction(
			DataCellStyleGeneratorFunction value) {
		this.value$contentStyleFunction$org$vaadin$addons$excelexporter$function$DataCellStyleGeneratorFunction = value;
		this.isSet$contentStyleFunction$org$vaadin$addons$excelexporter$function$DataCellStyleGeneratorFunction = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withIntegerFormattingProperties(List<String> value) {
		this.value$integerFormattingProperties$java$util$List = value;
		this.isSet$integerFormattingProperties$java$util$List = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withFloatFormattingProperties(List<String> value) {
		this.value$floatFormattingProperties$java$util$List = value;
		this.isSet$floatFormattingProperties$java$util$List = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withColumnFormatters(Map<Object, ColumnFormatter> value) {
		this.value$columnFormatters$java$util$Map = value;
		this.isSet$columnFormatters$java$util$Map = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withBooleanFormattingProperties(List<String> value) {
		this.value$booleanFormattingProperties$java$util$List = value;
		this.isSet$booleanFormattingProperties$java$util$List = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withGrid(Grid<BEANTYPE> value) {
		this.value$grid$com$vaadin$ui$Grid = value;
		this.isSet$grid$com$vaadin$ui$Grid = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withFooterConfigs(
			List<ComponentFooterConfiguration> value) {
		this.value$footerConfigs$java$util$List = value;
		this.isSet$footerConfigs$java$util$List = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withDateFormattingProperties(List<String> value) {
		this.value$dateFormattingProperties$java$util$List = value;
		this.isSet$dateFormattingProperties$java$util$List = true;
		return this.self;
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> withHeaderConfigs(
			List<ComponentHeaderConfiguration> value) {
		this.value$headerConfigs$java$util$List = value;
		this.isSet$headerConfigs$java$util$List = true;
		return this.self;
	}

	public Object clone() {
		try {
			ExportExcelComponentConfigurationBuilder<BEANTYPE> result = (ExportExcelComponentConfigurationBuilder) super.clone();
			result.self = result;
			return result;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	public ExportExcelComponentConfigurationBuilder<BEANTYPE> but() {
		return (ExportExcelComponentConfigurationBuilder) clone();
	}

	public ExportExcelComponentConfiguration<BEANTYPE> build() {
		ExportExcelComponentConfiguration<BEANTYPE> result = new ExportExcelComponentConfiguration();
		if (this.isSet$headerStyleFunction$java$util$function$BiFunction) {
			result.setHeaderStyleFunction(this.value$headerStyleFunction$java$util$function$BiFunction);
		}
		if (this.isSet$visibleProperties$java$lang$String$) {
			result.setVisibleProperties(this.value$visibleProperties$java$lang$String$);
		}
		if (this.isSet$footerStyleFunction$java$util$function$BiFunction) {
			result.setFooterStyleFunction(this.value$footerStyleFunction$java$util$function$BiFunction);
		}
		if (this.isSet$contentStyleFunction$org$vaadin$addons$excelexporter$function$DataCellStyleGeneratorFunction) {
			result.setContentStyleFunction(
					this.value$contentStyleFunction$org$vaadin$addons$excelexporter$function$DataCellStyleGeneratorFunction);
		}
		if (this.isSet$integerFormattingProperties$java$util$List) {
			result.setIntegerFormattingProperties(this.value$integerFormattingProperties$java$util$List);
		}
		if (this.isSet$floatFormattingProperties$java$util$List) {
			result.setFloatFormattingProperties(this.value$floatFormattingProperties$java$util$List);
		}
		if (this.isSet$columnFormatters$java$util$Map) {
			result.setColumnFormatters(this.value$columnFormatters$java$util$Map);
		}
		if (this.isSet$booleanFormattingProperties$java$util$List) {
			result.setBooleanFormattingProperties(this.value$booleanFormattingProperties$java$util$List);
		}
		if (this.isSet$grid$com$vaadin$ui$Grid) {
			result.setGrid(this.value$grid$com$vaadin$ui$Grid);
		}
		if (this.isSet$footerConfigs$java$util$List) {
			result.setFooterConfigs(this.value$footerConfigs$java$util$List);
		}
		if (this.isSet$dateFormattingProperties$java$util$List) {
			result.setDateFormattingProperties(this.value$dateFormattingProperties$java$util$List);
		}
		if (this.isSet$headerConfigs$java$util$List) {
			result.setHeaderConfigs(this.value$headerConfigs$java$util$List);
		}
		return result;
	}
}
