package software.simple.solutions.framework.core.web.components;

import com.github.appreciated.card.Card;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Hr;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class Panel extends Card {

	private static final long serialVersionUID = 8168312132263555041L;
	private Header header;

	public Panel() {
		header = new Header();
		header.getStyle().set("color", "var(--lumo-primary-color)");
		add(header);
		add(new Hr());
		getContent().setPadding(true);
	}

	public void setHeader(String header) {
		this.header.setText(header);
	}

	public void setHeaderKey(String key) {
		this.header.setText(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

}
