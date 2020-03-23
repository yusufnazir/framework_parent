package software.simple.solutions.framework.core.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;

/**
 * Information specific to a view.
 * 
 * @author yusuf
 *
 */
public class ViewDetail implements Serializable {

	private static final long serialVersionUID = 3347085150204226805L;
	private Long viewId;
	private Menu menu;
	private View view;
	private ActionState actionState;
	private boolean isEditing = false;
	private List<Menu> subMenus;
	private List<String> privileges;
	private String uuid;

	public ViewDetail() {
		uuid = UUID.randomUUID().toString();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Long getViewId() {
		return viewId;
	}

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	public ActionState getActionState() {
		return actionState;
	}

	public void setActionState(ActionState actionState) {
		this.actionState = actionState;
	}

	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
		if (this.view != null) {
			this.viewId = view.getId();
		}
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public List<String> getPrivileges() {
		if (this.privileges == null) {
			this.privileges = new ArrayList<String>();
		}
		return privileges;
	}

	public void setPrivileges(List<String> privileges) {
		if (this.privileges == null) {
			this.privileges = new ArrayList<String>();
		}
		this.privileges = privileges;
	}

	public void addPrivileges(String[] values) {
		if (privileges == null) {
			privileges = new ArrayList<>();
		}
		if (values != null && values.length > 0) {
			privileges.addAll(Arrays.asList(values));
		}
	}

}
