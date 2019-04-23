package software.simple.solutions.framework.core.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import software.simple.solutions.framework.core.event.EntityDeletedEvent;

@Component
public class EntityDeletedEventListener {

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	public void processEntityDeletedEvent(EntityDeletedEvent event) {
		System.out.println("running this method after a delete.");
	}
}
