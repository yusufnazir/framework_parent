package software.simple.solutions.framework.core.excelexporter.configuration.builder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelComponentConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelSheetConfiguration;

public class ExportExcelSheetConfigurationBuilder<BEANTYPE> implements Cloneable {
	protected ExportExcelSheetConfigurationBuilder<BEANTYPE> self;
	protected Map<String, String> value$additionalHeaderInfo$java$util$Map;
	protected boolean isSet$additionalHeaderInfo$java$util$Map = false;
	protected XSSFCellStyle value$additionalHeaderCaptionStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle;
	protected boolean isSet$additionalHeaderCaptionStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = false;
	protected Boolean value$isDefaultGeneratedByRequired$java$lang$Boolean;
	protected boolean isSet$isDefaultGeneratedByRequired$java$lang$Boolean = false;
	protected Boolean value$isHeaderSectionRequired$java$lang$Boolean;
	protected boolean isSet$isHeaderSectionRequired$java$lang$Boolean = false;
	protected Integer[] value$columnForTitleRegion$java$lang$Integer$;
	protected boolean isSet$columnForTitleRegion$java$lang$Integer$ = false;
	protected Integer value$noOfColumnsInAddHeader$java$lang$Integer;
	protected boolean isSet$noOfColumnsInAddHeader$java$lang$Integer = false;
	protected Integer value$headerCaptionStartCol$java$lang$Integer;
	protected boolean isSet$headerCaptionStartCol$java$lang$Integer = false;
	protected Integer value$noOfColumnsInHeader$java$lang$Integer;
	protected boolean isSet$noOfColumnsInHeader$java$lang$Integer = false;
	protected ExportExcelConfiguration<BEANTYPE> value$exportExcelConfiguration$org$vaadin$addons$excelexporter$configuration$ExportExcelConfiguration;
	protected boolean isSet$exportExcelConfiguration$org$vaadin$addons$excelexporter$configuration$ExportExcelConfiguration = false;
	protected Boolean value$isTotalRowRequired$java$lang$Boolean;
	protected boolean isSet$isTotalRowRequired$java$lang$Boolean = false;
	protected String value$reportTitle$java$lang$String;
	protected boolean isSet$reportTitle$java$lang$String = false;
	protected Function<XSSFWorkbook, XSSFCellStyle> value$reportTitleStyleFunction$java$util$function$Function;
	protected boolean isSet$reportTitleStyleFunction$java$util$function$Function = false;
	protected String value$dateFormat$java$lang$String;
	protected boolean isSet$dateFormat$java$lang$String = false;
	protected Function<XSSFWorkbook, XSSFCellStyle> value$generatedByStyleFunction$java$util$function$Function;
	protected boolean isSet$generatedByStyleFunction$java$util$function$Function = false;
	protected Integer value$headerValueStartCol$java$lang$Integer;
	protected boolean isSet$headerValueStartCol$java$lang$Integer = false;
	protected XSSFCellStyle value$headerValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle;
	protected boolean isSet$headerValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = false;
	protected Boolean value$isDefaultSheetTitleRequired$java$lang$Boolean;
	protected boolean isSet$isDefaultSheetTitleRequired$java$lang$Boolean = false;
	protected int value$frozenRows$int;
	protected boolean isSet$frozenRows$int = false;
	protected String value$loggerInfoRowContent$java$lang$String;
	protected boolean isSet$loggerInfoRowContent$java$lang$String = false;
	protected List<ExportExcelComponentConfiguration<BEANTYPE>> value$componentConfigs$java$util$List;
	protected boolean isSet$componentConfigs$java$util$List = false;
	protected Integer value$noOfColumnsToMergeInAddHeaderValue$java$lang$Integer;
	protected boolean isSet$noOfColumnsToMergeInAddHeaderValue$java$lang$Integer = false;
	protected Integer[] value$columnForGeneratedByRegion$java$lang$Integer$;
	protected boolean isSet$columnForGeneratedByRegion$java$lang$Integer$ = false;
	protected String value$reportTitleRowContent$java$lang$String;
	protected boolean isSet$reportTitleRowContent$java$lang$String = false;
	protected int value$frozenColumns$int;
	protected boolean isSet$frozenColumns$int = false;
	protected XSSFCellStyle value$additionalHeaderValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle;
	protected boolean isSet$additionalHeaderValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = false;
	protected String value$sheetName$java$lang$String;
	protected boolean isSet$sheetName$java$lang$String = false;

	public ExportExcelSheetConfigurationBuilder() {
		this.self = this;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withAdditionalHeaderInfo(Map<String, String> value) {
		this.value$additionalHeaderInfo$java$util$Map = value;
		this.isSet$additionalHeaderInfo$java$util$Map = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withAdditionalHeaderCaptionStyle(XSSFCellStyle value) {
		this.value$additionalHeaderCaptionStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = value;
		this.isSet$additionalHeaderCaptionStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withIsDefaultGeneratedByRequired(Boolean value) {
		this.value$isDefaultGeneratedByRequired$java$lang$Boolean = value;
		this.isSet$isDefaultGeneratedByRequired$java$lang$Boolean = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withIsHeaderSectionRequired(Boolean value) {
		this.value$isHeaderSectionRequired$java$lang$Boolean = value;
		this.isSet$isHeaderSectionRequired$java$lang$Boolean = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withColumnForTitleRegion(Integer[] value) {
		this.value$columnForTitleRegion$java$lang$Integer$ = value;
		this.isSet$columnForTitleRegion$java$lang$Integer$ = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withNoOfColumnsInAddHeader(Integer value) {
		this.value$noOfColumnsInAddHeader$java$lang$Integer = value;
		this.isSet$noOfColumnsInAddHeader$java$lang$Integer = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withHeaderCaptionStartCol(Integer value) {
		this.value$headerCaptionStartCol$java$lang$Integer = value;
		this.isSet$headerCaptionStartCol$java$lang$Integer = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withNoOfColumnsInHeader(Integer value) {
		this.value$noOfColumnsInHeader$java$lang$Integer = value;
		this.isSet$noOfColumnsInHeader$java$lang$Integer = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withExportExcelConfiguration(
			ExportExcelConfiguration<BEANTYPE> value) {
		this.value$exportExcelConfiguration$org$vaadin$addons$excelexporter$configuration$ExportExcelConfiguration = value;
		this.isSet$exportExcelConfiguration$org$vaadin$addons$excelexporter$configuration$ExportExcelConfiguration = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withIsTotalRowRequired(Boolean value) {
		this.value$isTotalRowRequired$java$lang$Boolean = value;
		this.isSet$isTotalRowRequired$java$lang$Boolean = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withReportTitle(String value) {
		this.value$reportTitle$java$lang$String = value;
		this.isSet$reportTitle$java$lang$String = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withReportTitleStyleFunction(
			Function<XSSFWorkbook, XSSFCellStyle> value) {
		this.value$reportTitleStyleFunction$java$util$function$Function = value;
		this.isSet$reportTitleStyleFunction$java$util$function$Function = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withDateFormat(String value) {
		this.value$dateFormat$java$lang$String = value;
		this.isSet$dateFormat$java$lang$String = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withGeneratedByStyleFunction(
			Function<XSSFWorkbook, XSSFCellStyle> value) {
		this.value$generatedByStyleFunction$java$util$function$Function = value;
		this.isSet$generatedByStyleFunction$java$util$function$Function = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withHeaderValueStartCol(Integer value) {
		this.value$headerValueStartCol$java$lang$Integer = value;
		this.isSet$headerValueStartCol$java$lang$Integer = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withHeaderValueStyle(XSSFCellStyle value) {
		this.value$headerValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = value;
		this.isSet$headerValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withIsDefaultSheetTitleRequired(Boolean value) {
		this.value$isDefaultSheetTitleRequired$java$lang$Boolean = value;
		this.isSet$isDefaultSheetTitleRequired$java$lang$Boolean = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withFrozenRows(int value) {
		this.value$frozenRows$int = value;
		this.isSet$frozenRows$int = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withLoggerInfoRowContent(String value) {
		this.value$loggerInfoRowContent$java$lang$String = value;
		this.isSet$loggerInfoRowContent$java$lang$String = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withComponentConfigs(
			List<ExportExcelComponentConfiguration<BEANTYPE>> value) {
		this.value$componentConfigs$java$util$List = value;
		this.isSet$componentConfigs$java$util$List = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withNoOfColumnsToMergeInAddHeaderValue(Integer value) {
		this.value$noOfColumnsToMergeInAddHeaderValue$java$lang$Integer = value;
		this.isSet$noOfColumnsToMergeInAddHeaderValue$java$lang$Integer = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withColumnForGeneratedByRegion(Integer[] value) {
		this.value$columnForGeneratedByRegion$java$lang$Integer$ = value;
		this.isSet$columnForGeneratedByRegion$java$lang$Integer$ = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withReportTitleRowContent(String value) {
		this.value$reportTitleRowContent$java$lang$String = value;
		this.isSet$reportTitleRowContent$java$lang$String = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withFrozenColumns(int value) {
		this.value$frozenColumns$int = value;
		this.isSet$frozenColumns$int = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withAdditionalHeaderValueStyle(XSSFCellStyle value) {
		this.value$additionalHeaderValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = value;
		this.isSet$additionalHeaderValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle = true;
		return this.self;
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> withSheetName(String value) {
		this.value$sheetName$java$lang$String = value;
		this.isSet$sheetName$java$lang$String = true;
		return this.self;
	}

	public Object clone() {
		try {
			ExportExcelSheetConfigurationBuilder<BEANTYPE> result = (ExportExcelSheetConfigurationBuilder) super.clone();
			result.self = result;
			return result;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	public ExportExcelSheetConfigurationBuilder<BEANTYPE> but() {
		return (ExportExcelSheetConfigurationBuilder) clone();
	}

	public ExportExcelSheetConfiguration<BEANTYPE> build() {
		ExportExcelSheetConfiguration<BEANTYPE> result = new ExportExcelSheetConfiguration();
		if (this.isSet$additionalHeaderInfo$java$util$Map) {
			result.setAdditionalHeaderInfo(this.value$additionalHeaderInfo$java$util$Map);
		}
		if (this.isSet$additionalHeaderCaptionStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle) {
			result.setAdditionalHeaderCaptionStyle(
					this.value$additionalHeaderCaptionStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle);
		}
		if (this.isSet$isDefaultGeneratedByRequired$java$lang$Boolean) {
			result.setIsDefaultGeneratedByRequired(this.value$isDefaultGeneratedByRequired$java$lang$Boolean);
		}
		if (this.isSet$isHeaderSectionRequired$java$lang$Boolean) {
			result.setIsHeaderSectionRequired(this.value$isHeaderSectionRequired$java$lang$Boolean);
		}
		if (this.isSet$columnForTitleRegion$java$lang$Integer$) {
			result.setColumnForTitleRegion(this.value$columnForTitleRegion$java$lang$Integer$);
		}
		if (this.isSet$noOfColumnsInAddHeader$java$lang$Integer) {
			result.setNoOfColumnsInAddHeader(this.value$noOfColumnsInAddHeader$java$lang$Integer);
		}
		if (this.isSet$headerCaptionStartCol$java$lang$Integer) {
			result.setHeaderCaptionStartCol(this.value$headerCaptionStartCol$java$lang$Integer);
		}
		if (this.isSet$noOfColumnsInHeader$java$lang$Integer) {
			result.setNoOfColumnsInHeader(this.value$noOfColumnsInHeader$java$lang$Integer);
		}
		if (this.isSet$exportExcelConfiguration$org$vaadin$addons$excelexporter$configuration$ExportExcelConfiguration) {
			result.setExportExcelConfiguration(
					this.value$exportExcelConfiguration$org$vaadin$addons$excelexporter$configuration$ExportExcelConfiguration);
		}
		if (this.isSet$isTotalRowRequired$java$lang$Boolean) {
			result.setIsTotalRowRequired(this.value$isTotalRowRequired$java$lang$Boolean);
		}
		if (this.isSet$reportTitle$java$lang$String) {
			result.setReportTitle(this.value$reportTitle$java$lang$String);
		}
		if (this.isSet$reportTitleStyleFunction$java$util$function$Function) {
			result.setReportTitleStyleFunction(this.value$reportTitleStyleFunction$java$util$function$Function);
		}
		if (this.isSet$dateFormat$java$lang$String) {
			result.setDateFormat(this.value$dateFormat$java$lang$String);
		}
		if (this.isSet$generatedByStyleFunction$java$util$function$Function) {
			result.setGeneratedByStyleFunction(this.value$generatedByStyleFunction$java$util$function$Function);
		}
		if (this.isSet$headerValueStartCol$java$lang$Integer) {
			result.setHeaderValueStartCol(this.value$headerValueStartCol$java$lang$Integer);
		}
		if (this.isSet$headerValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle) {
			result.setHeaderValueStyle(this.value$headerValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle);
		}
		if (this.isSet$isDefaultSheetTitleRequired$java$lang$Boolean) {
			result.setIsDefaultSheetTitleRequired(this.value$isDefaultSheetTitleRequired$java$lang$Boolean);
		}
		if (this.isSet$frozenRows$int) {
			result.setFrozenRows(this.value$frozenRows$int);
		}
		if (this.isSet$loggerInfoRowContent$java$lang$String) {
			result.setLoggerInfoRowContent(this.value$loggerInfoRowContent$java$lang$String);
		}
		if (this.isSet$componentConfigs$java$util$List) {
			result.setComponentConfigs(this.value$componentConfigs$java$util$List);
		}
		if (this.isSet$noOfColumnsToMergeInAddHeaderValue$java$lang$Integer) {
			result.setNoOfColumnsToMergeInAddHeaderValue(
					this.value$noOfColumnsToMergeInAddHeaderValue$java$lang$Integer);
		}
		if (this.isSet$columnForGeneratedByRegion$java$lang$Integer$) {
			result.setColumnForGeneratedByRegion(this.value$columnForGeneratedByRegion$java$lang$Integer$);
		}
		if (this.isSet$reportTitleRowContent$java$lang$String) {
			result.setReportTitleRowContent(this.value$reportTitleRowContent$java$lang$String);
		}
		if (this.isSet$frozenColumns$int) {
			result.setFrozenColumns(this.value$frozenColumns$int);
		}
		if (this.isSet$additionalHeaderValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle) {
			result.setAdditionalHeaderValueStyle(
					this.value$additionalHeaderValueStyle$org$apache$poi$xssf$usermodel$XSSFCellStyle);
		}
		if (this.isSet$sheetName$java$lang$String) {
			result.setSheetName(this.value$sheetName$java$lang$String);
		}
		return result;
	}
}
