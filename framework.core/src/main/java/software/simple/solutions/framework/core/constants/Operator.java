package software.simple.solutions.framework.core.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Search operators.
 * 
 * @author yusuf
 *
 */
public class Operator {
	public static final String EQ = "=";
	public static final String GT = ">";
	public static final String GE = ">=";
	public static final String LT = "<";
	public static final String LE = "<=";
	public static final String NE = "≠";
	public static final String UE = "!=";
	public static final String BE = "[]";
	public static final String SW = "?*";
	public static final String EW = "*?";
	public static final String CT = "*?*";
	public static final String NCT = "!*?*";
	public static final String IN = "IN";
	public static final String NIN = "NOT IN";
	public static final String NULL = "NULL";
	public static final String NNULL = "NOT NULL";

	private static final String[] PRIVATE_DATE_OPERATORS = { EQ, GT, GE, LT, LE, NE, BE };
	private static final String[] PRIVATE_STRING_OPERATORS = { EQ, NE, EW, SW, CT, NCT };
	private static final String[] PRIVATE_MULTI_STRING_OPERATORS = { IN, NIN, NULL, NNULL };
	private static final String[] PRIVATE_SINGLE_NUMBER_OPERATORS = { EQ, NE, GT, GE, LT, LE };
	private static final String[] PRIVATE_NUMBER_OPERATORS = { EQ, GT, GE, LT, LE, NE, BE };

	public static final List<String> NUMBER_OPERATORS = Collections
			.unmodifiableList(Arrays.asList(PRIVATE_NUMBER_OPERATORS));
	public static final List<String> DATE_OPERATORS = Collections
			.unmodifiableList(Arrays.asList(PRIVATE_DATE_OPERATORS));
	public static final List<String> STRING_OPERATORS = Collections
			.unmodifiableList(Arrays.asList(PRIVATE_STRING_OPERATORS));
	public static final List<String> MULTI_STRING_OPERATORS = Collections
			.unmodifiableList(Arrays.asList(PRIVATE_MULTI_STRING_OPERATORS));
	public static final List<String> SINGLE_NUMBER_OPERATORS = Collections
			.unmodifiableList(Arrays.asList(PRIVATE_SINGLE_NUMBER_OPERATORS));
}
