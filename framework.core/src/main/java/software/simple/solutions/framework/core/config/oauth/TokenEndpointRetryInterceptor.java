package software.simple.solutions.framework.core.config.oauth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import software.simple.solutions.framework.core.service.IApplicationUserService;

@Aspect
@Component
public class TokenEndpointRetryInterceptor {

	private static final int MAX_RETRIES = 4;

	@Autowired
	private IApplicationUserService applicationUserService;

	@Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.*(..))")
	public Object execute(ProceedingJoinPoint aJoinPoint) throws Throwable {
		int tts = 1000;
		for (int i = 0; i < MAX_RETRIES; i++) {
			try {
				return aJoinPoint.proceed();
			} catch (DuplicateKeyException e) {
				String message = e.getMessage();
				Pattern p = Pattern.compile("(?:^|\\s)'([^']*?)'(?:$|\\s)", Pattern.MULTILINE);
				Matcher m = p.matcher(message);
				if (m.find()) {
					applicationUserService.removeOauthAccessToken(m.group(0).replaceAll("'", "").trim());
				}
				Thread.sleep(tts);
				tts = tts * 2;
			}
		}
		throw new IllegalStateException("Could not execute: " + aJoinPoint.getSignature().getName());
	}

}
