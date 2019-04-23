package software.simple.solutions.framework.core.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumberUtil implements Serializable {

	private static final long serialVersionUID = -3282113591971416914L;
	private static final Logger logger = LogManager.getLogger(NumberUtil.class);

	public static boolean isNumeric(String value) {
		return value.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
	}

	public static DecimalFormat formatterByLocale(Locale locale) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(locale);
		formatter.setDecimalSeparatorAlwaysShown(true);
		formatter.applyPattern("###,##0.00");
		return formatter;
	}

	public static String format(Locale locale, Object value) {
		Float fl = new Float(String.valueOf(value));
		String format = formatterByLocale(locale).format(fl);
		return format;
	}

	public static String parseLocalized(Locale locale, Object value) {
		DecimalFormat formatter = formatterByLocale(locale);
		char decimalSeparator = formatter.getDecimalFormatSymbols().getDecimalSeparator();

		String number = String.valueOf(value);
		number = number.replace('.', decimalSeparator);
		return number;
	}

	public static char getDecimalSeparator(Locale locale) {
		return formatterByLocale(locale).getDecimalFormatSymbols().getDecimalSeparator();
	}

	public static char getGroupingSeparator(Locale locale) {
		return formatterByLocale(locale).getDecimalFormatSymbols().getGroupingSeparator();
	}

	public static String round(Object value, int precision) {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
		formatter.setDecimalSeparatorAlwaysShown(true);
		formatter.applyPattern("###,##0.00");
		double pow = Math.pow(10.0D, precision);
		Float fl = new Float(String.valueOf(value));
		fl = new Float(Math.round(fl.floatValue() * pow) / pow);
		return formatter.format(fl);
	}

	public static Long getLong(Object value) {
		if (value != null && value.toString().length() > 0) {
			try {
				return Long.valueOf(String.valueOf(value));
			} catch (NumberFormatException e) {
				// logger.error("Value cannot be parsed to Long [" + value +
				// "]");
				return null;
			}
		}
		return null;
	}

	public static Integer getInteger(Object value) {
		if (value != null && value.toString().length() > 0) {
			try {
				return Integer.valueOf(String.valueOf(value));
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	public static Integer getInteger(Object value, int defaultValueIfNull) {
		Integer integer = getInteger(value);
		if (integer == null) {
			return defaultValueIfNull;
		}
		return integer;
	}

	public static BigDecimal getBigDecimal(Object value) {
		if ((value != null) && (value.toString().length() > 0)) {
			try {
				return new BigDecimal(value.toString());
			} catch (NumberFormatException e) {
				// logger.error("Value cannot be parsed to BigDecimal [" + value
				// + "]");
				return null;
			}
		}
		return null;
	}

	public static Float getFloat(Object value) {
		if (value != null && value.toString().length() > 0) {
			try {
				return Float.valueOf(String.valueOf(value));
			} catch (NumberFormatException e) {
				// logger.error("Value cannot be parsed to Float [" + value +
				// "]");
				return null;
			}
		}
		return null;
	}
}
