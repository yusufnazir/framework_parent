package software.simple.solutions.framework.core.excelexporter.configuration.builder;

import software.simple.solutions.framework.core.excelexporter.configuration.MergedCell;

public class MergedCellBuilder implements Cloneable {
	protected MergedCellBuilder self;
	protected String value$endProperty$java$lang$String;
	protected boolean isSet$endProperty$java$lang$String = false;
	protected String value$startProperty$java$lang$String;
	protected boolean isSet$startProperty$java$lang$String = false;
	protected String value$headerKey$java$lang$String;
	protected boolean isSet$headerKey$java$lang$String = false;

	public MergedCellBuilder() {
		this.self = this;
	}

	public MergedCellBuilder withEndProperty(String value) {
		this.value$endProperty$java$lang$String = value;
		this.isSet$endProperty$java$lang$String = true;
		return this.self;
	}

	public MergedCellBuilder withStartProperty(String value) {
		this.value$startProperty$java$lang$String = value;
		this.isSet$startProperty$java$lang$String = true;
		return this.self;
	}

	public MergedCellBuilder withHeaderKey(String value) {
		this.value$headerKey$java$lang$String = value;
		this.isSet$headerKey$java$lang$String = true;
		return this.self;
	}

	public Object clone() {
		try {
			MergedCellBuilder result = (MergedCellBuilder) super.clone();
			result.self = result;
			return result;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}

	public MergedCellBuilder but() {
		return (MergedCellBuilder) clone();
	}

	public MergedCell build() {
		MergedCell result = new MergedCell();
		if (this.isSet$endProperty$java$lang$String) {
			result.setEndProperty(this.value$endProperty$java$lang$String);
		}
		if (this.isSet$startProperty$java$lang$String) {
			result.setStartProperty(this.value$startProperty$java$lang$String);
		}
		if (this.isSet$headerKey$java$lang$String) {
			result.setHeaderKey(this.value$headerKey$java$lang$String);
		}
		return result;
	}
}
