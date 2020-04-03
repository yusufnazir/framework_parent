package software.simple.solutions.framework.core.components.select;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.IRelationTypeService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.components.CComboBox;

public class RelationTypeSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	private static final Logger logger = LogManager.getLogger(RelationTypeSelect.class);

	public RelationTypeSelect() {
		IRelationTypeService relationTypeService = ContextProvider.getBean(IRelationTypeService.class);
		List<ComboItem> items;
		try {
			items = relationTypeService.getForListing(RelationType.class, true);
			items.stream().forEach(p -> p.setName(PropertyResolver.getPropertyValueByLocale(ReferenceKey.RELATION_TYPE,
					p.getId().toString(), UI.getCurrent().getLocale(), null, p.getName())));
			setItems(items);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
