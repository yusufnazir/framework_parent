package software.simple.solutions.framework.core.web.view.audit;

import com.vaadin.ui.Component;

import software.simple.solutions.framework.core.pojo.RevisionPojo;

public interface RevisionViewGenerator {

	Component getRevisionView(RevisionPojo revisionPojo);

}
