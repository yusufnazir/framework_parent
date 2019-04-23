package software.simple.solutions.framework.core.components;

import java.util.List;

import software.simple.solutions.framework.core.pojo.ComboItem;

public interface Listable {

	public void setList(List<ComboItem> list);

	public void setList(List<ComboItem> list, ComboItem selected);
}
