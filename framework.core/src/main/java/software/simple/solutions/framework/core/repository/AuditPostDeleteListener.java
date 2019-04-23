package software.simple.solutions.framework.core.repository;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.envers.event.spi.EnversPostDeleteEventListenerImpl;
import org.hibernate.event.spi.PostDeleteEvent;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

public class AuditPostDeleteListener extends EnversPostDeleteEventListenerImpl {

	public AuditPostDeleteListener(EnversService enversService) {
		super(enversService);
	}

	private static final long serialVersionUID = 1078306847150561079L;

//	@TransactionalEventListener(phase=TransactionPhase.AFTER_COMPLETION)
	@Override
	public void onPostDelete(PostDeleteEvent event) {
		Object entity = event.getEntity();
		Object[] deletedState = event.getDeletedState();
//		deletedState = changeUpdatedByUser(entity, event.getPersister(), deletedState);
		super.onPostDelete(
				new PostDeleteEvent(entity, event.getId(), deletedState, event.getPersister(), event.getSession()));
	}

	// public static Object[] changeUpdatedByUser(Object entity, EntityPersister
	// entityPersister, Object[] deleteState) {
	// ApplicationUser updatedByUser = ((IMappedSuperClass)
	// entity).getUpdatedByUser();
	// for (Field field : entity.getClass().getSuperclass().getDeclaredFields())
	// {
	// if (field.getName().equalsIgnoreCase("updatedByUser")) {
	// int index = 0;
	// for (AttributeDefinition ad : entityPersister.getAttributes()) {
	// if (ad.getName().equalsIgnoreCase(field.getName())) {
	// deleteState[index] = updatedByUser;
	// break;
	// }
	// index++;
	// }
	// break;
	// }
	// }
	// return deleteState;
	// }
}
