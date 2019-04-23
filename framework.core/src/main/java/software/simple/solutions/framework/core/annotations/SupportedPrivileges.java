package software.simple.solutions.framework.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import software.simple.solutions.framework.core.constants.Privileges;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedPrivileges {

	String[] privileges() default { Privileges.INSERT, Privileges.UPDATE, Privileges.DELETE, Privileges.SEARCH };

	String[] extraPrivileges() default "";
}
