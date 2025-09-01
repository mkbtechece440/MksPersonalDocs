package ext.plural.builders;

import java.util.ArrayList;
import java.util.List;

import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TableConfig;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.RelationalExpression;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;


//For below @ComponentBuilder Annotation we are passing the component id value.
@ComponentBuilder("ext.plural.builders.TableBuilderExample")
public class TableBuilderExample extends AbstractComponentBuilder {
	
	
	//Below method is used for handling the Object data.
	@Override
	public Object buildComponentData(ComponentConfig arg0, ComponentParams arg1) throws Exception {
		// TODO Auto-generated method stub
		WTPart part = null;

		List<WTPart> findingsList = new ArrayList<WTPart>();
		String[] LinkedFindingNumber = {"EP-010920251147", "EP-010920251149", "EP-010920251150"};

		QuerySpec qSpec = new QuerySpec(WTPart.class);
		ClassAttribute numberID = new ClassAttribute(WTPart.class, WTPart.NUMBER);
		qSpec.appendWhere(new SearchCondition(numberID, SearchCondition.IN, (RelationalExpression) new ArrayExpression(LinkedFindingNumber)),
				new int[] { 0 });
		QueryResult queryResultOfFindings = PersistenceHelper.manager.find(qSpec);

		while (queryResultOfFindings.hasMoreElements()) {
			part = (WTPart) queryResultOfFindings.nextElement();
			part = (WTPart) VersionControlHelper.service.allIterationsOf(part.getMaster()).nextElement();
			findingsList.add(part);
		}
		return findingsList;
	}
	
	//Below method is used for handling the table level and column level configurations
	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0) throws WTException {
		// TODO Auto-generated method stub
		ComponentConfigFactory factory = getComponentConfigFactory();

		TableConfig tableConfig = factory.newTableConfig();
		tableConfig.setLabel("Table Builder");
		tableConfig.setShowCount(true);
		tableConfig.setSelectable(true);
		tableConfig.setConfigurable(true);

		ColumnConfig colName = factory.newColumnConfig();
		colName.setLabel("Name");
		colName.setId("name");
		tableConfig.addComponent(colName);

		ColumnConfig colNumber = factory.newColumnConfig();
		colNumber.setLabel("Number");
		colNumber.setId("number");
		tableConfig.addComponent(colNumber);

		ColumnConfig colIsEndItem = factory.newColumnConfig();
		colIsEndItem.setLabel("End Items");
		colIsEndItem.setId("endItem");
		tableConfig.addComponent(colIsEndItem);

		return tableConfig;
	}
}

