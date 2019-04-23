package software.simple.solutions.framework.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CxodeConfigurationComponent {

	public int order() default 0;
	public String captionKey();

}
