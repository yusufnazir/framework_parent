package software.simple.solutions.framework.core.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
public @interface MailPlaceholders {

}
