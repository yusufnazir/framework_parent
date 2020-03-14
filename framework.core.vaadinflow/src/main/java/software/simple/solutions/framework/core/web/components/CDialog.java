package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.dialog.Dialog;

public class CDialog extends Dialog {

	private static final long serialVersionUID = 8471635741190363049L;
	private Object data;

	public <T> T getData() {
		return (T) data;
	}

	public <T> void setData(T data) {
		this.data = data;
	}

}
