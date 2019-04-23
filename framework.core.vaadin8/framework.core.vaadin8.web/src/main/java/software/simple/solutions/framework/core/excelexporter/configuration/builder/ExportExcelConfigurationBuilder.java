package software.simple.solutions.framework.core.excelexporter.configuration.builder;

import java.util.List;

import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelSheetConfiguration;

public class ExportExcelConfigurationBuilder<BEANTYPE>
  implements Cloneable
{
  protected ExportExcelConfigurationBuilder<BEANTYPE> self;
  protected List<ExportExcelSheetConfiguration<BEANTYPE>> value$sheetConfigs$java$util$List;
  protected boolean isSet$sheetConfigs$java$util$List = false;
  protected String value$exportFileName$java$lang$String;
  protected boolean isSet$exportFileName$java$lang$String = false;
  protected String value$generatedBy$java$lang$String;
  protected boolean isSet$generatedBy$java$lang$String = false;
  protected int value$maxFilenameCalendarExtension$int;
  protected boolean isSet$maxFilenameCalendarExtension$int = false;
  
  public ExportExcelConfigurationBuilder()
  {
    this.self = this;
  }
  
  public ExportExcelConfigurationBuilder<BEANTYPE> withSheetConfigs(List<ExportExcelSheetConfiguration<BEANTYPE>> value)
  {
    this.value$sheetConfigs$java$util$List = value;
    this.isSet$sheetConfigs$java$util$List = true;
    return this.self;
  }
  
  public ExportExcelConfigurationBuilder<BEANTYPE> withExportFileName(String value)
  {
    this.value$exportFileName$java$lang$String = value;
    this.isSet$exportFileName$java$lang$String = true;
    return this.self;
  }
  
  public ExportExcelConfigurationBuilder<BEANTYPE> withGeneratedBy(String value)
  {
    this.value$generatedBy$java$lang$String = value;
    this.isSet$generatedBy$java$lang$String = true;
    return this.self;
  }
  
  public ExportExcelConfigurationBuilder<BEANTYPE> withMaxFilenameCalendarExtension(int value)
  {
    this.value$maxFilenameCalendarExtension$int = value;
    this.isSet$maxFilenameCalendarExtension$int = true;
    return this.self;
  }
  
  public Object clone()
  {
    try
    {
      ExportExcelConfigurationBuilder<BEANTYPE> result = (ExportExcelConfigurationBuilder)super.clone();
      result.self = result;
      return result;
    }
    catch (CloneNotSupportedException e)
    {
      throw new InternalError(e.getMessage());
    }
  }
  
  public ExportExcelConfigurationBuilder<BEANTYPE> but()
  {
    return (ExportExcelConfigurationBuilder)clone();
  }
  
  public ExportExcelConfiguration<BEANTYPE> build()
  {
    ExportExcelConfiguration<BEANTYPE> result = new ExportExcelConfiguration();
    if (this.isSet$sheetConfigs$java$util$List) {
      result.setSheetConfigs(this.value$sheetConfigs$java$util$List);
    }
    if (this.isSet$exportFileName$java$lang$String) {
      result.setExportFileName(this.value$exportFileName$java$lang$String);
    }
    if (this.isSet$generatedBy$java$lang$String) {
      result.setGeneratedBy(this.value$generatedBy$java$lang$String);
    }
    if (this.isSet$maxFilenameCalendarExtension$int) {
      result.setMaxFilenameCalendarExtension(this.value$maxFilenameCalendarExtension$int);
    }
    return result;
  }
}
