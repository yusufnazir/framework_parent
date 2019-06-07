package software.simple.solutions.framework.core.components.select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.pojo.ComboItem;

public class ApplicationUserSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public void setValue(ApplicationUser applicationUser) {
		if (applicationUser != null) {
			setValue(applicationUser.getId());
		}
	}

	public void setValues(List<ApplicationUser> applicationUsers) {
		List<ComboItem> items = applicationUsers.stream().map(p -> new ComboItem(p.getId(), p.getUsername()))
				.collect(Collectors.toList());
		setItems(items);
	}

	public void removeAllItems() {
		setItems(new ArrayList<ComboItem>());
	}

}
