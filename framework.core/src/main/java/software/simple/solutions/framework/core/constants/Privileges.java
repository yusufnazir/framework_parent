package software.simple.solutions.framework.core.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import software.simple.solutions.framework.core.annotations.SupportedPrivileges;

public class Privileges {

	public static final String INSERT = "INSERT";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	public static final String SEARCH = "SEARCH";
	
	public static final String PERSON_SHOW_GENDER = "PERSON_SHOW_GENDER";
	public static final String PERSON_SHOW_DOB = "PERSON_SHOW_DOB";

	public static final List<String> DEFAULT_PRIVILEGES = Arrays.asList(INSERT, UPDATE, DELETE, SEARCH);

	public static List<String> getPrivilegeCodes(String viewClassName) {
		List<String> privilegeCodes = new ArrayList<String>();
		try {
			Class<?> class1 = Class.forName(viewClassName);
			SupportedPrivileges supportedPrivileges = class1.getAnnotation(SupportedPrivileges.class);
			if (supportedPrivileges != null) {
				String[] privileges = supportedPrivileges.privileges();
				if (privileges != null && privileges.length > 0) {
					privilegeCodes.addAll(Arrays.asList(privileges));
				} else {
					privilegeCodes.addAll(Privileges.DEFAULT_PRIVILEGES);
				}

				String[] extraPrivileges = supportedPrivileges.extraPrivileges();
				if (extraPrivileges != null && extraPrivileges.length > 0) {
					privilegeCodes.addAll(Arrays.asList(extraPrivileges));
				}
			}else{
				privilegeCodes.addAll(Privileges.DEFAULT_PRIVILEGES);
			}

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return privilegeCodes;
	}
}
