package software.simple.solutions.framework.core.util;

public class ThreadContext {

	public static final ThreadLocal<ThreadAttributes> myThreadLocal = new ThreadLocal<ThreadAttributes>();

	public static void add(ThreadAttributes threadAttributes) {
		myThreadLocal.set(threadAttributes);
	}

	public static void remove() {
		myThreadLocal.remove();
	}

	public static ThreadAttributes get() {
		return myThreadLocal.get();
	}

}
