package software.simple.solutions.framework.core.repository;

import java.util.Date;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import software.simple.solutions.framework.core.entities.UserRevEntity;

public class CustomRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		UserRevEntity userRevEntity = (UserRevEntity) revisionEntity;

		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			userRevEntity.setUsername("BACKGROUND-PROCESSOR");
			userRevEntity.setTimestamp(new Date().getTime());
		} else {
			Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
			String username = loggedInUser.getName();
			userRevEntity.setUsername(username);
			userRevEntity.setTimestamp(new Date().getTime());
		}
	}

}
