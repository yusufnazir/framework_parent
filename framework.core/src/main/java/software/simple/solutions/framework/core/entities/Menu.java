package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.google.common.collect.ComparisonChain;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.MenuProperty;
import software.simple.solutions.framework.core.properties.ViewProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.MENU.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Menu extends MappedSuperClass implements Comparable<Menu> {

	private static final long serialVersionUID = -720087732122778727L;

	@Id
	@TableGenerator(
			name = "table",
			table = "sequences_",
			pkColumnName = "PK_NAME",
			valueColumnName = "PK_VALUE",
			initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@FilterFieldProperty(fieldProperty = MenuProperty.ID)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = MenuProperty.CODE)
	@Column(name = CxodeTables.MENU.COLUMNS.CODE)
	private String code;

	@FilterFieldProperty(fieldProperty = MenuProperty.NAME)
	@Column(name = CxodeTables.MENU.COLUMNS.NAME)
	private String name;

	@FilterFieldProperty(fieldProperty = MenuProperty.DESCRIPTION)
	@Column(name = CxodeTables.MENU.COLUMNS.DESCRIPTION)
	private String description;

	@FilterFieldProperty(fieldProperty = ViewProperty.ID)
	@ManyToOne()
	@JoinColumn(name = CxodeTables.MENU.COLUMNS.VIEW_ID)
	private View view;

	@Column(name = CxodeTables.MENU.COLUMNS.INDEX)
	private Long index;

	@Column(name = CxodeTables.MENU.COLUMNS.ICON)
	private String icon;

	@FilterFieldProperty(fieldProperty = MenuProperty.TYPE)
	@Column(name = CxodeTables.MENU.COLUMNS.TYPE)
	private Long type;

	@FilterFieldProperty(fieldProperty = MenuProperty.PARENT_MENU, referencedFieldProperty = MenuProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.MENU.COLUMNS.PARENT_MENU_ID, referencedColumnName = ID_)
	private Menu parentMenu;

	@FilterFieldProperty(fieldProperty = MenuProperty.ACTIVE)
	@Column(name = CxodeTables.MENU.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getCaption() {
		StringBuilder caption = new StringBuilder();
		if (code != null && code.length() > 0) {
			caption.append("[").append(code).append("]");
		}
		if (name != null && name.length() > 0) {
			caption.append(" ").append(name);
		}

		return caption.toString();
	}

	@Override
	public int compareTo(Menu o) {
		return ComparisonChain.start().compare(this.getId(), o.getId()).result();
	}

}
