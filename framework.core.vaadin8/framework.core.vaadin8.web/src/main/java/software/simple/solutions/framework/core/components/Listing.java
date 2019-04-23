package software.simple.solutions.framework.core.components;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;

public class Listing implements Serializable {

	private static final long serialVersionUID = 3188054605162520186L;

	private static List<ComboItem> createList(List<String> list) {
		return list.stream().map(p -> new ComboItem(p)).collect(Collectors.toList());
	}

	public static CComboBox getDateOperatorSelect(CComboBox combo) {
		combo.setItems(createList(Operator.DATE_OPERATORS));
		return combo;
	}

	public static CComboBox getNumberOperatorSelect(CComboBox combo) {
		combo.setItems(createList(Operator.NUMBER_OPERATORS));
		return combo;
	}

	public static CComboBox getStringOperatorSelect(CComboBox combo) {
		combo.setItems(createList(Operator.STRING_OPERATORS));
		return combo;
	}
	
	public static CComboBox getMultiStringOperatorSelect(CComboBox combo) {
		combo.setItems(createList(Operator.MULTI_STRING_OPERATORS));
		return combo;
	}
}
