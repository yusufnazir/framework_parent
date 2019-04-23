package software.simple.solutions.framework.core.valueobjects;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.LanguageProperty;

public class LanguageVO extends SuperVO implements Serializable {

	private static final long serialVersionUID = -3367794797373562555L;

	private static final Logger logger = LogManager.getLogger(LanguageVO.class);

	private Long id;

	@FilterFieldProperty(fieldProperty = LanguageProperty.CODE)
	private StringInterval codeInterval;

	@FilterFieldProperty(fieldProperty = LanguageProperty.NAME)
	private StringInterval nameInterval;

	@FilterFieldProperty(fieldProperty = LanguageProperty.DESCRIPTION)
	private StringInterval descriptionInterval;

	@FilterFieldProperty(fieldProperty = LanguageProperty.ACTIVE)
	private Boolean active;

	private String code;
	private String name;
	private String description;

	private byte[] flag;
	private Long flag16Id;
	private Long flag32Id;
	private FlagResolution flagResolution;

	public enum FlagResolution {
		FLAG_16, FLAG_32;
	}

	public LanguageVO() {
		super();
	}

	public byte[] getFlag() {
		return flag;
	}

	public void setFlag(byte[] flag) {
		this.flag = flag;
	}

	public Long getFlag16Id() {
		return flag16Id;
	}

	public void setFlag16Id(Long flag16Id) {
		this.flag16Id = flag16Id;
	}

	public Long getFlag32Id() {
		return flag32Id;
	}

	public void setFlag32Id(Long flag32Id) {
		this.flag32Id = flag32Id;
	}

	public FlagResolution getFlagResolution() {
		return flagResolution;
	}

	public void setFlagResolution(FlagResolution flagResolution) {
		this.flagResolution = flagResolution;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	public StringInterval getCodeInterval() {
		return codeInterval;
	}

	public void setCodeInterval(StringInterval codeInterval) {
		this.codeInterval = codeInterval;
	}

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getDescriptionInterval() {
		return descriptionInterval;
	}

	public void setDescriptionInterval(StringInterval descriptionInterval) {
		this.descriptionInterval = descriptionInterval;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
