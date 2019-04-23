package software.simple.solutions.framework.core.config.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IApplicationUserRepository;

@Service
public class SimpleUserDetailsService implements UserDetailsService {

	private final Map<String, UserDetails> users = new ConcurrentHashMap<>();

	@Autowired
	private IApplicationUserRepository applicationUserRepository;

	SimpleUserDetailsService() {
		Arrays.asList("josh", "rob", "joe")
				.forEach(username -> this.users.putIfAbsent(username,
						new User(username, "$2a$10$WFqkIMKDW9u0bPysCvrwjurXDVeIESGMrRh/JifPq7XDQ/oUBmyDi", true, true,
								true, true, AuthorityUtils.createAuthorityList("USER"))));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

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
