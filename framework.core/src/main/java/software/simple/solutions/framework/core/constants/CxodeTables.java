package software.simple.solutions.framework.core.constants;

public class CxodeTables {

	public final class APPLICATION_USER {

		public static final String NAME = "APPLICATION_USERS_";

		public final class COLUMNS {
			public static final String USERNAME = "USERNAME_";
			public static final String PASSWORD = "PASSWORD_";
			public static final String FORCE_CHANGE_PASSWORD = "FORCE_CHANGE_PASSWORD_";
			public static final String PASSWORD_CHANGE_DATE = "PASSWORD_CHANGE_DATE_";
			public static final String PERSON_ID = "PERSON_ID_";
			public static final String ALIAS = "ALIAS_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String USE_LDAP_ = "USE_LDAP_";
		}
	}

	public final class APPLICATION_USER_CONFIGURATION {

		public static final String NAME = "APPLICATION_USER_CONFIGURATIONS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String APPLICATION_USER_ID = "APPLICATION_USER_ID_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VALUE = "VALUE_";
			public static final String BIG_VALUE = "BIG_VALUE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";

		}
	}

	public final class APPLICATION_USER_REQUEST_PASSWORD_RESET {

		public static final String NAME = "APPLICATION_USER_REQUEST_PASSWORD_RESET_";

		public final class COLUMNS {
			public static final String RESET_KEY = "RESET_KEY_";
			public static final String VALID_DATE_TIME = "VALID_DATE_TIME_";
			public static final String APPLICATION_USER_ID = "APPLICATION_USER_ID_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class CONFIGURATION {

		public static final String NAME = "CONFIGURATIONS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VALUE = "VALUE_";
			public static final String BIG_VALUE = "BIG_VALUE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";

		}
	}
	
	public final class COUNTRY {

		public static final String NAME = "COUNTRIES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ALPHA2 = "ALPHA2_";
			public static final String ALPHA3 = "ALPHA3_";
			public static final String NAME = "NAME_";
			public static final String ACTIVE = "ACTIVE_";
		}
	}

	public final class CURRENCY {

		public static final String NAME = "CURRENCIES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VALUE = "VALUE_";
			public static final String BIG_VALUE = "BIG_VALUE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class ENTITY_FILES_ {

		public static final String NAME = "ENTITY_FILES_";

		public final class COLUMNS {
			public static final String NAME_ = "NAME_";
			public static final String FILE_ = "FILE_";
			public static final String ENTITY_ID_ = "ENTITY_ID_";
			public static final String ENTITY_NAME_ = "ENTITY_NAME_";
			public static final String TYPE_OF_FILE_ = "TYPE_OF_FILE_";
			public static final String FILE_PATH_ = "FILE_PATH_";
			public static final String ACTIVE_ = "ACTIVE_";
		}
	}

	public final class GENDER {

		public static final String NAME = "GENDERS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VALUE = "VALUE_";
			public static final String BIG_VALUE = "BIG_VALUE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String NAME_ = "NAME_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class GENERATED_MAIL {

		public static final String NAME = "GENERATED_MAILS_";

		public final class COLUMNS {
			public static final String FROM = "FROM_";
			public static final String TO = "TO_";
			public static final String SUBJECT = "SUBJECT_";
			public static final String MESSAGE = "MESSAGE_";
			public static final String ACTIVE = "ACTIVE_";
		}
	}

	public final class LANGUAGE {

		public static final String NAME = "LANGUAGES_";

		public final class COLUMNS {
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class MAIL_TEMPLATE {

		public static final String NAME = "MAIL_TEMPLATES_";

		public final class COLUMNS {
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String SUBJECT = "SUBJECT_";
			public static final String MESSAGE = "MESSAGE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String STATE = "STATE_";
			public static final String RETRY_COUNT = "RETRY_COUNT_";
			public static final String ERROR_MESSAGE = "ERROR_MESSAGE_";
		}
	}

	public final class MENU {

		public static final String NAME = "MENUS_";

		public final class COLUMNS {
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VIEW_ID = "VIEW_ID_";
			public static final String INDEX = "INDEX_";
			public static final String ICON = "ICON_";
			public static final String TYPE = "TYPE_";
			public static final String PARENT_MENU_ID = "PARENT_MENU_ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class MENU_SETTING {

		public static final String NAME = "MENU_SETTINGS_";

		public final class COLUMNS {
			public static final String TYPE = "TYPE_";
			public static final String VALUE_ = "VALUE_";
			public static final String MENU_ID = "MENU_ID_";
			public static final String ACTIVE = "ACTIVE_";
		}
	}

	public final class MESSAGE {

		public static final String NAME = "MESSAGES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VALUE = "VALUE_";
			public static final String BIG_VALUE = "BIG_VALUE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class MESSAGES_PER_LOCALE {

		public static final String NAME = "MESSAGES_PER_LOCALES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SUBJECT = "SUBJECT_";
			public static final String REASON = "REASON_";
			public static final String REMEDY = "REMEDY_";
			public static final String LOCALE_ID = "LOCALE_ID_";
			public static final String MESSAGE_ID = "MESSAGE_ID_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class PERSON {

		public static final String NAME = "PERSONS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String FIRST_NAME = "FIRST_NAME_";
			public static final String MIDDLE_NAME = "MIDDLE_NAME_";
			public static final String LAST_NAME = "LAST_NAME_";
			public static final String DATE_OF_BIRTH = "DATE_OF_BIRTH_";
			public static final String PLACE_OF_BIRTH = "PLACE_OF_BIRTH_";
			public static final String GENDER_ID = "GENDER_ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}
	
	public final class PERSON_EMERGENCY_CONTACT {

		public static final String NAME = "PERSON_EMERGENCY_CONTACTS_";

		public final class COLUMNS {
			public static final String PERSON_ID = "PERSON_ID_";
			public static final String NAME = "NAME_";
			public static final String RELATIONSHIP = "RELATIONSHIP_";
			public static final String CONTACT_NUMBER = "CONTACT_NUMBER_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class PERSON_INFORMATION {

		public static final String NAME = "PERSON_INFORMATION_";

		public final class COLUMNS {
			public static final String CODE = "CODE_";
			public static final String VALUE = "VALUE_";
			public static final String PERSON_ID = "PERSON_ID_";
			public static final String PRIMARY_EMAIL = "PRIMARY_EMAIL_";
			public static final String SECONDARY_EMAIL = "SECONDARY_EMAIL_";
			public static final String PRIMARY_CONTACT_NUMBER = "PRIMARY_CONTACT_NUMBER_";
			public static final String SECONDARY_CONTACT_NUMBER = "SECONDARY_CONTACT_NUMBER_";
			public static final String STREET_ADDRESS = "STREET_ADDRESS_";
			public static final String CITY = "CITY_";
			public static final String STATE = "STATE_";
			public static final String POSTAL_CODE = "POSTAL_CODE_";
			public static final String COUNTRY_ID = "COUNTRY_ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class PERSON_RELATIONS {

		public static final String NAME = "PERSON_RELATIONS_";

		public final class COLUMNS {
			public static final String PERSON_ID = "PERSON_ID_";
			public static final String RELATION_TYPE_ID = "RELATION_TYPE_ID_";
			public static final String RELATION_ID = "RELATION_ID_";
			public static final String START_DATE = "START_DATE_";
			public static final String END_DATE = "END_DATE_";
		}
	}

	public final class PROPERTY {

		public static final String NAME = "PROPERTIES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String KEY = "KEY_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class PROPERTY_PER_LOCALE {

		public static final String NAME = "PROPERTIES_PER_LOCALES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String LOCALE_ID = "LOCALE_ID_";
//			public static final String PROPERTY_ID = "PROPERTY_ID_";
			public static final String REFERENCE_KEY = "REFERENCE_KEY_";
			public static final String REFERENCE_ID = "REFERENCE_ID_";
			public static final String VALUE = "VALUE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class RELATION_TYPE_ {

		public static final String NAME = "RELATION_TYPES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String REQUIRES_RELATED_TO_ = "REQUIRES_RELATED_TO_";
			public static final String KEY_ = "KEY_";
		}
	}

	public final class ROLE {

		public static final String NAME = "ROLES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
			public static final String ROLE_CATEGORY_ID_ = "ROLE_CATEGORY_ID_";
		}
	}

	public final class ROLE_CATEGORY {

		public static final String NAME = "ROLE_CATEGORIES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String NAME = "NAME_";
		}
	}

	public final class PRIVILEGE {

		public static final String NAME = "PRIVILEGES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String CODE_ = "CODE_";
			public static final String KEY_ = "KEY_";
		}
	}

	public final class ROLE_PRIVILEGE {

		public static final String NAME = "ROLE_PRIVILEGES_";

		public final class COLUMNS {
			public static final String ID_ = "ID_";
			public static final String ACTIVE_ = "ACTIVE_";
			public static final String ROLE_ID_ = "ROLE_ID_";
			public static final String PRIVILEGE_ID_ = "PRIVILEGE_ID_";
		}
	}

	public final class ROLE_VIEW {

		public static final String NAME = "ROLE_VIEWS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String ROLE_ID = "ROLE_ID_";
			public static final String VIEW_ID = "VIEW_ID_";
		}
	}

	public final class ROLE_VIEW_PRIVILEGE {

		public static final String NAME = "ROLE_VIEW_PRIVILEGES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ROLE_VIEW_ID_ = "ROLE_VIEW_ID_";
			public static final String PRIVILEGE_ID_ = "PRIVILEGE_ID_";
		}
	}

	public final class SUB_MENU {

		public static final String NAME = "SUB_MENUS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String PARENT_MENU_ID = "PARENT_MENU_ID_";
			public static final String CHILD_MENU_ID = "CHILD_MENU_ID_";
			public static final String INDEX = "INDEX_";
			public static final String TYPE = "TYPE_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class USER_ROLE {

		public static final String NAME = "USER_ROLES_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String USER_ID = "USER_ID_";
			public static final String ROLE_ID = "ROLE_ID_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

	public final class VIEW {

		public static final String NAME = "VIEWS_";

		public final class COLUMNS {
			public static final String ID = "ID_";
			public static final String ACTIVE = "ACTIVE_";
			public static final String CODE = "CODE_";
			public static final String NAME = "NAME_";
			public static final String DESCRIPTION = "DESCRIPTION_";
			public static final String VIEW_CLASS_NAME = "VIEW_CLASS_NAME_";
			public static final String SOFT_DELETE = "SOFT_DELETE_";
		}
	}

}
