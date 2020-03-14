package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IViewService extends ISuperService {

	View getByClassName(String name) throws FrameworkException;

}
