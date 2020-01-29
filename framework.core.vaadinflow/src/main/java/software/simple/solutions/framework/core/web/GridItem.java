package software.simple.solutions.framework.core.web;

import com.vaadin.flow.function.ValueProvider;

public class GridItem<K, V> {

	private ValueProvider<K, V> valueProvider;
	private String captionKey;
	private boolean isComponent = false;
	private int width;

	public GridItem() {
		super();
	}

	public GridItem(ValueProvider<K, V> valueProvider, String captionKey) {
		this();
		this.valueProvider = valueProvider;
		this.captionKey = captionKey;
	}

	public ValueProvider<K, V> getValueProvider() {
		return valueProvider;
	}

	public void setValueProvider(ValueProvider<K, V> valueProvider) {
		this.valueProvider = valueProvider;
	}

	public String getCaptionKey() {
		return captionKey;
	}

	public void setCaptionKey(String captionKey) {
		this.captionKey = captionKey;
	}

	public boolean isComponent() {
		return isComponent;
	}

	public void setComponent(boolean isComponent) {
		this.isComponent = isComponent;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
