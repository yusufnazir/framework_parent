package software.simple.solutions.framework.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IApplicationUserRepository;

/**
 * Created by suman.das on 11/28/18.
 */
// @Component
//@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private IApplicationUserRepository applicationUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		ApplicationUser user = null;
		try {
			user = applicationUserRepository.getByUserName(username);
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (user == null) {
			throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT"));
		// user.getRoles().forEach(role -> {
		// authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		// });

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}
}
