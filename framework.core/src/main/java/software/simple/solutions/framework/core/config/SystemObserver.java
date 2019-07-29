package software.simple.solutions.framework.core.config;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.pojo.EntityUpdate;

@Component
public class SystemObserver {

	private final BehaviorSubject<Boolean> applicationLogoChangeObserver;
	private final BehaviorSubject<Boolean> applicationConsolidateRoleChangeObserver;
	private final BehaviorSubject<Pair<String, EntityUpdate>> entityUpdatedChangeObserver;

	public SystemObserver() {
		applicationLogoChangeObserver = BehaviorSubject.create();
		applicationConsolidateRoleChangeObserver = BehaviorSubject.create();
		entityUpdatedChangeObserver = BehaviorSubject.create();
	}

	public BehaviorSubject<Boolean> getApplicationLogoChangeObserver() {
		return applicationLogoChangeObserver;
	}

	public BehaviorSubject<Boolean> getApplicationConsolidateRoleChangeObserver() {
		return applicationConsolidateRoleChangeObserver;
	}

	public BehaviorSubject<Pair<String, EntityUpdate>> getEntityUpdatedChangeObserver() {
		return entityUpdatedChangeObserver;
	}

}
