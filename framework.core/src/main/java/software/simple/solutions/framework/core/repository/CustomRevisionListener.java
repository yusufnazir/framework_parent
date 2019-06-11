package software.simple.solutions.framework.core.repository;

import java.util.Date;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import software.simple.solutions.framework.core.entities.UserRevEntity;

public class CustomRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		UserRevEntity userRevEntity = (UserRevEntity) revisionEntity;
		// ThreadAttributes threadAttributes = ThreadContext.get();
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		Object principal = loggedInUser.getPrincipal();
		userRevEntity.setUsername(username);
		userRevEntity.setTimestamp(new Date().getTime());
	}

}
