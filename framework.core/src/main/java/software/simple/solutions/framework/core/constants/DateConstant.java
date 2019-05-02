package software.simple.solutions.framework.core.constants;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class DateConstant {
	
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd – kk:mm");

}
