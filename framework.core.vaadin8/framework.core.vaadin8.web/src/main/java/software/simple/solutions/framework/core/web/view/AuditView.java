package software.simple.solutions.framework.core.web.view;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.web.AuditTemplate;

public class AuditView extends AuditTemplate {

//	public void prePopulateResultsTable(List<?> results) throws FrameworkException {
//		if (results != null && !results.isEmpty() && !isColumnsCreated()) {
//			setColumnsCreated(true);
//
//			Object[] result = (Object[]) results.get(0);
//
//			getContentTable().addContainerProperty("Revision", Integer.class);
//
//			getContentTable().addContainerProperty("Operation", String.class);
//
//			Object object = result[0];
//			Field[] fields = object.getClass().getDeclaredFields();
//			for (Field field : fields) {
//				if (field.isAnnotationPresent(Column.class)) {
//					Column column = field.getAnnotation(Column.class);
//					String name = column.name();
//					getContentTable().addContainerProperty(name, field.getType());
//				} else if (field.isAnnotationPresent(ManyToOne.class)) {
//					JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
//					String name = joinColumn.name();
//					getContentTable().addContainerProperty(name, Serializable.class);
//				}
//			}
//		}
//	}

//	@Override
//	public void populateRow(Object itemId, Object result)
//			throws FrameworkException, IllegalArgumentException, IllegalAccessException {
//		Object[] object = (Object[]) result;
//
//		DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) object[1];
//		setContainerPropertyValue(itemId, "Revision", defaultRevisionEntity.getId());
//
//		RevisionType revisionType = (RevisionType) object[2];
//		setContainerPropertyValue(itemId, "Operation", revisionType.name());
//
//		Object obj = object[0];
//		Field[] fields = obj.getClass().getDeclaredFields();
//		for (Field field : fields) {
//			if (field.isAnnotationPresent(Column.class)) {
//				Column column = field.getAnnotation(Column.class);
//				String name = column.name();
//				field.setAccessible(true);
//				setContainerPropertyValue(itemId, name, field.get(obj));
//			} else if (field.isAnnotationPresent(ManyToOne.class)) {
//				JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
//				String name = joinColumn.name();
//				field.setAccessible(true);
//				Object object2 = field.get(obj);
//				Serializable identifier = getIdentifier(object2);
//				setContainerPropertyValue(itemId, name, identifier);
//			}
//		}
//	}

	public Serializable getIdentifier(Object object) {

		if (!(object instanceof HibernateProxy) || Hibernate.isInitialized(object)) {
			return ((IMappedSuperClass) object).getId();
		}

		HibernateProxy proxy = (HibernateProxy) object;
		LazyInitializer initializer = proxy.getHibernateLazyInitializer();
		return initializer.getIdentifier();
	}
}
