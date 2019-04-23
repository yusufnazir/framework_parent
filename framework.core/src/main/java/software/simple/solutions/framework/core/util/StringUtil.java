package software.simple.solutions.framework.core.util;

import java.util.Arrays;
import java.util.List;

public class StringUtil {

	private static List<Character> specialCharacters = Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
			'-', '_', '=', '+', '~', '`', '[', ']', '{', '}', '\\', '|', '<', '>', '?', '/');

	public static boolean containsSpecial(Character ch) {
		return specialCharacters.stream().anyMatch(s -> s.compareTo(ch) == 0);
	}
}
