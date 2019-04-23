package software.simple.solutions.framework.core.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.envers.boot.internal.EnversService;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.internal.SessionFactoryImpl;

import software.simple.solutions.framework.core.repository.AuditPostDeleteListener;

//@Component
public class HibernateListenerConfigurer {
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	protected void init() {
		SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
		EnversService enversService = sessionFactory.getServiceRegistry().getService(EnversService.class);
		registry.setListeners(EventType.POST_DELETE,
				new PostDeleteEventListener[] { new AuditPostDeleteListener(enversService) });
	}
}
