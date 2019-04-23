package software.simple.solutions.framework.core.config.bak;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * Created by suman.das on 11/28/18.
 */
//@Configuration
//@EnableResourceServer
//@Order(3)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private ResourceServerTokenServices tokenServices;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// http.antMatcher("/**")
		// .authorizeRequests().antMatchers("/", "/login**", "/webjars/**",
		// "/oauth**", "/oauth/authorize",
		// "/oauth/confirm_access", "/oauth/token")
		// .permitAll().anyRequest().authenticated().and().formLogin().permitAll();
		// .and().exceptionHandling()
		// .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
		http.requiresChannel().anyRequest().requiresSecure();
		http.requestMatchers().and().authorizeRequests()
				.antMatchers("/actuator/**", "/api-docs/**", "/oauth/*", "/app/**", "/vaadinServlet/**", "/VAADIN/**",
						"/HEARTBEAT/**", "/UIDL/**", "/resources/**", "/oauth/**", "/login**")
				.permitAll().antMatchers("/jwttest/**").authenticated().anyRequest().authenticated().and().formLogin()
				.permitAll().and().exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

		AuthenticationEntryPoint aep = new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				response.sendRedirect("/login");
			}
		};

		// http.exceptionHandling().authenticationEntryPoint(aep);
	}

}
