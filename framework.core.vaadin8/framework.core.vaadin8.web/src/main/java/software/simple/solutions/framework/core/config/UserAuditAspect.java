package software.simple.solutions.framework.core.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserAuditAspect extends AspectBeforeAdvice {

	@Before("execution(* software.simple.solutions.framework.core.service.impl.*Service.*(..))")
	public void before(JoinPoint joinPoint) {
		// advice
		advice();
	}

}
