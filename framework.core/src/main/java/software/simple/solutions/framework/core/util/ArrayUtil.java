package software.simple.solutions.framework.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayUtil {

	public static <T> List<T> asList(T... a) {
		if (a == null || a.length == 0) {
			return null;
		}
		List<T> list = Arrays.asList(a);
		return list.stream().filter(p -> p != null).collect(Collectors.toList());
	}
}
