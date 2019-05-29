package software.simple.solutions.framework.core.config;

import org.springframework.stereotype.Component;

import io.reactivex.subjects.BehaviorSubject;

@Component
public class SystemObserver {

	private final BehaviorSubject<Boolean> applicationLogoChangeObserver;

	public SystemObserver() {
		applicationLogoChangeObserver = BehaviorSubject.create();
	}

	public BehaviorSubject<Boolean> getApplicationLogoChangeObserver() {
		return applicationLogoChangeObserver;
	}

}
