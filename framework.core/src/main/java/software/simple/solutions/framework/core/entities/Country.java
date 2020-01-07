package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = CxodeTables.COUNTRY.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Country extends MappedSuperClass {

	private static final long serialVersionUID = -2647578391650603709L;

	@Id
	@TableGenerator(
			name = "table",
			table = "sequences_",
			pkColumnName = "PK_NAME",
			valueColumnName = "PK_VALUE",
			initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.COUNTRY.COLUMNS.ALPHA2)
	private String alpha2;

	@Column(name = CxodeTables.COUNTRY.COLUMNS.ALPHA3)
	private String alpha3;

	@Column(name = CxodeTables.COUNTRY.COLUMNS.NAME)
	private String name;

	@Column(name = CxodeTables.CURRENCY.COLUMNS.ACTIVE)
	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlpha2() {
		return alpha2;
	}

	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}

	public String getAlpha3() {
		return alpha3;
	}

	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((alpha2 == null) ? 0 : alpha2.hashCode());
		result = prime * result + ((alpha3 == null) ? 0 : alpha3.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (alpha2 == null) {
			if (other.alpha2 != null)
				return false;
		} else if (!alpha2.equals(other.alpha2))
			return false;
		if (alpha3 == null) {
			if (other.alpha3 != null)
				return false;
		} else if (!alpha3.equals(other.alpha3))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
