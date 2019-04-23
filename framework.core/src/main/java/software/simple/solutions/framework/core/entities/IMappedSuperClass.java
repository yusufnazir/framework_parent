package software.simple.solutions.framework.core.entities;

public interface IMappedSuperClass {

	public Long getId();

	public void setId(Long id);

	public Boolean getActive();

	public void setActive(Boolean active);

	public String getCaption();

	public ApplicationUser getUpdatedByUser();

	public void setUpdatedByUser(ApplicationUser updatedByUser);
}
