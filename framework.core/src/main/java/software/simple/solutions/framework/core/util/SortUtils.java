package software.simple.solutions.framework.core.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SortUtils {

	private static final Logger logger = LogManager.getLogger(SortUtils.class);

	public static int compareTo(Object o1, Object o2) {
		if (((o1 instanceof String)) && ((o2 instanceof String))) {
			return compareStrings((String) o1, (String) o2);
		} else if (((o1 instanceof Long)) && ((o2 instanceof Long))) {
			return compareLongs(o1, o2);
		} else if (((o1 instanceof BigDecimal)) && ((o2 instanceof BigDecimal))) {
			return compareBigDecimal(o1, o2);
		} else if (((o1 instanceof Boolean)) && ((o2 instanceof Boolean))) {
			return compareBoolean(o1, o2);
		}
		return 0;
	}

	public static int compareStrings(String o1, String o2) {
		// BigDecimal bigDecimal1 = NumberUtil.getBigDecimal(o1);
		// BigDecimal bigDecimal2 = NumberUtil.getBigDecimal(o2);
		// if (o1 != null && bigDecimal1 != null && o2 != null && bigDecimal2 !=
		// null) {
		// return bigDecimal1.compareTo(bigDecimal2);
		// } else {
		return ObjectUtils.compare(o1, o2);
		// return ((String) o1).toLowerCase().compareTo(((String)
		// o2).toLowerCase());
		// }
	}

	public static int compareLongs(Object o1, Object o2) {
		return ((Long) o1).compareTo((Long) o2);
	}

	public static int compareIntegers(Object o1, Object o2) {
		return ((Integer) o1).compareTo((Integer) o2);
	}

	public static int compareBigDecimal(Object o1, Object o2) {
		return ((BigDecimal) o1).compareTo((BigDecimal) o2);
	}

	public static int compareBoolean(Object o1, Object o2) {
		return ((Boolean) o1).compareTo((Boolean) o2);
	}

}
