package software.simple.solutions.framework.core.listeners;

import com.google.common.eventbus.Subscribe;

import software.simple.solutions.framework.core.events.LoginSuccessfullEvent;

/**
 * Listener for a successfull login.
 * 
 * @author yusuf
 *
 */
public class LoginSuccessfullListener {

	/**
	 * Fired when the login has been successful.
	 * 
	 * @param event
	 */
	@Subscribe
	public void handle(LoginSuccessfullEvent event) {
		event.handleEvent();
	}

}
