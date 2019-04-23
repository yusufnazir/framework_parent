package software.simple.solutions.framework.core.repository;

import org.hibernate.envers.RevisionListener;

import software.simple.solutions.framework.core.entities.UserRevEntity;
import software.simple.solutions.framework.core.util.ThreadAttributes;
import software.simple.solutions.framework.core.util.ThreadContext;

public class CustomRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		UserRevEntity userRevEntity = (UserRevEntity) revisionEntity;
		ThreadAttributes threadAttributes = ThreadContext.get();
		userRevEntity.setUsername(threadAttributes == null ? "SYSTEM" : threadAttributes.getUsername());
	}

}
