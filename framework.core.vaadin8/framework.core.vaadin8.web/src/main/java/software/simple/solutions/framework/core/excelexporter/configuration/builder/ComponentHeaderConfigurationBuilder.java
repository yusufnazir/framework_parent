package software.simple.solutions.framework.core.excelexporter.configuration.builder;

import java.util.List;

import com.vaadin.ui.components.grid.HeaderRow;

import software.simple.solutions.framework.core.excelexporter.configuration.ComponentHeaderConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.MergedCell;

public class ComponentHeaderConfigurationBuilder
  implements Cloneable
{
  protected ComponentHeaderConfigurationBuilder self;
  protected HeaderRow value$row$com$vaadin$ui$components$grid$HeaderRow;
  protected boolean isSet$row$com$vaadin$ui$components$grid$HeaderRow = false;
  protected List<MergedCell> value$mergedCells$java$util$List;
  protected boolean isSet$mergedCells$java$util$List = false;
  protected boolean value$autoFilter$boolean;
  protected boolean isSet$autoFilter$boolean = false;
  protected String[] value$columnKeys$java$lang$String$;
  protected boolean isSet$columnKeys$java$lang$String$ = false;
  protected Boolean value$defaultConfig$java$lang$Boolean;
  protected boolean isSet$defaultConfig$java$lang$Boolean = false;
  
  public ComponentHeaderConfigurationBuilder()
  {
    this.self = this;
  }
  
  public ComponentHeaderConfigurationBuilder withRow(HeaderRow value)
  {
    this.value$row$com$vaadin$ui$components$grid$HeaderRow = value;
    this.isSet$row$com$vaadin$ui$components$grid$HeaderRow = true;
    return this.self;
  }
  
  public ComponentHeaderConfigurationBuilder withMergedCells(List<MergedCell> value)
  {
    this.value$mergedCells$java$util$List = value;
    this.isSet$mergedCells$java$util$List = true;
    return this.self;
  }
  
  public ComponentHeaderConfigurationBuilder withAutoFilter(boolean value)
  {
    this.value$autoFilter$boolean = value;
    this.isSet$autoFilter$boolean = true;
    return this.self;
  }
  
  public ComponentHeaderConfigurationBuilder withColumnKeys(String[] value)
  {
    this.value$columnKeys$java$lang$String$ = value;
    this.isSet$columnKeys$java$lang$String$ = true;
    return this.self;
  }
  
  public ComponentHeaderConfigurationBuilder withDefaultConfig(Boolean value)
  {
    this.value$defaultConfig$java$lang$Boolean = value;
    this.isSet$defaultConfig$java$lang$Boolean = true;
    return this.self;
  }
  
  public Object clone()
  {
    try
    {
      ComponentHeaderConfigurationBuilder result = (ComponentHeaderConfigurationBuilder)super.clone();
      result.self = result;
      return result;
    }
    catch (CloneNotSupportedException e)
    {
      throw new InternalError(e.getMessage());
    }
  }
  
  public ComponentHeaderConfigurationBuilder but()
  {
    return (ComponentHeaderConfigurationBuilder)clone();
  }
  
  public ComponentHeaderConfiguration build()
  {
    ComponentHeaderConfiguration result = new ComponentHeaderConfiguration();
    if (this.isSet$row$com$vaadin$ui$components$grid$HeaderRow) {
      result.setRow(this.value$row$com$vaadin$ui$components$grid$HeaderRow);
    }
    if (this.isSet$mergedCells$java$util$List) {
      result.setMergedCells(this.value$mergedCells$java$util$List);
    }
    if (this.isSet$autoFilter$boolean) {
      result.setAutoFilter(this.value$autoFilter$boolean);
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
