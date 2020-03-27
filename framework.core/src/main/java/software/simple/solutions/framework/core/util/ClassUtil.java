package software.simple.solutions.framework.core.util;

public class ClassUtil {

	@SuppressWarnings("unchecked")
	public static <T> T getEntity(Object[] result, Class<T> cl) {
		for (Object o : result) {
			if (o != null && cl.isAssignableFrom(o.getClass())) {
				return (T) o;
			}
		}
		return null;
	}
}
