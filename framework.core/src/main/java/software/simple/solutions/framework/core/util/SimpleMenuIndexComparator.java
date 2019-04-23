package software.simple.solutions.framework.core.util;

import java.util.Comparator;

import software.simple.solutions.framework.core.pojo.SimpleMenuItem;

public class SimpleMenuIndexComparator implements Comparator<SimpleMenuItem> {

	@Override
	public int compare(SimpleMenuItem menu1, SimpleMenuItem menu2) {
		if (menu1 != null && menu2 != null) {
			Long index1 = menu1.getIndex();
			Long index2 = menu2.getIndex();
			if (index1 != null && index2 != null) {
				return Long.compare(index1, index2);
			}
		}
		return 0;
	}

}
