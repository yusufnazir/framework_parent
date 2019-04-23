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

import software.simple.solutions.framework.core.constants.CxodeTables;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.SUB_MENU.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class SubMenu extends MappedSuperClass {

	private static final long serialVersionUID = -720087732122778727L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = CxodeTables.SUB_MENU.COLUMNS.PARENT_MENU_ID)
	private Menu parentMenu;

	@ManyToOne()
	@JoinColumn(name = CxodeTables.SUB_MENU.COLUMNS.CHILD_MENU_ID)
	private Menu childMenu;

	@Column(name = CxodeTables.SUB_MENU.COLUMNS.INDEX)
	private Integer index;

	@Column(name = CxodeTables.SUB_MENU.COLUMNS.TYPE)
	private Long type;

	@Column(name = CxodeTables.SUB_MENU.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public Menu getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(Menu childMenu) {
		this.childMenu = childMenu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubMenu other = (SubMenu) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (childMenu == null) {
			if (other.childMenu != null)
				return false;
		} else if (!childMenu.equals(other.childMenu))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (parentMenu == null) {
			if (other.parentMenu != null)
				return false;
		} else if (!parentMenu.equals(other.parentMenu))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
