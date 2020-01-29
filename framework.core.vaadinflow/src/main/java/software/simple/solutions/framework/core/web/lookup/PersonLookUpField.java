package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.web.LookUpField;

public class PersonLookUpField extends LookUpField {

	private static final long serialVersionUID = 994848491488378790L;

	public PersonLookUpField() {
		super();
		setEntityClass(Person.class);
//		setViewClass(PersonView.class);
	}

}
