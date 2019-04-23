package software.simple.solutions.framework.core.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IApplicationUserService;

@RestController
@RequestMapping("/rest")
public class ResourceController {

	@Autowired
	private IApplicationUserService applicationUserService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<ApplicationUser> getUsers() throws FrameworkException {
		return applicationUserService.findAll();
	}

}
