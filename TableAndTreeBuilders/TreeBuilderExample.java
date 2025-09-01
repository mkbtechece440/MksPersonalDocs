package ext.plural.builders;

import com.ptc.mvc.components.AbstractComponentBuilder;
import com.ptc.mvc.components.ColumnConfig;
import com.ptc.mvc.components.ComponentBuilder;
import com.ptc.mvc.components.ComponentConfig;
import com.ptc.mvc.components.ComponentConfigFactory;
import com.ptc.mvc.components.ComponentParams;
import com.ptc.mvc.components.TreeConfig;

import wt.util.WTException;

@ComponentBuilder("ext.plural.builders.TreeBuilderExample")
public class TreeBuilderExample extends AbstractComponentBuilder {

	@Override
	public Object buildComponentData(ComponentConfig arge, ComponentParams arg1) throws Exception {
		return new CustomTreeHandlerAdapter();
	}

	@Override
	public ComponentConfig buildComponentConfig(ComponentParams arg0) throws WTException {
		System.out.println("Inside buildComponentConfig() of WTPartTreeBuilderExample class!!");

		final ComponentConfigFactory componentConfig = getComponentConfigFactory();
		final TreeConfig tableConfig =componentConfig.newTreeConfig();

		tableConfig.setId("ext.plural.builders.TreeBuilderExample");

		tableConfig.setLabel("Tree Builders");
		tableConfig.setShowCount(true); tableConfig.setSelectable(true);

		tableConfig.setSingleSelect(false);

		// tableConfig.setActionModel("part_report_toolbar_actions");
		tableConfig.addComponent(componentConfig.newColumnConfig("type_icon", true));

		tableConfig.addComponent(componentConfig.newColumnConfig("number", true));

		final ColumnConfig localColumnConfig1 =componentConfig.newColumnConfig("name", true);
		localColumnConfig1.setWidth(50); 
		localColumnConfig1.setInfoPageLink(true);

		//localColumnConfig1.setDataUtilityId("CustomABSNameDataUtility");
		tableConfig.addComponent(localColumnConfig1);

		final ColumnConfig localColumnConfig2 =  componentConfig.newColumnConfig("number", true);
		localColumnConfig2.setWidth(50);
		localColumnConfig2.setLabel("Created By");
		tableConfig.addComponent(localColumnConfig2);

		tableConfig.addComponent(componentConfig.newColumnConfig("version", true));

		tableConfig.addComponent(componentConfig.newColumnConfig("thePersistInfo.modifyStamp", true));

		final ColumnConfig localColumnConfig3  = componentConfig.newColumnConfig("state", true);
		localColumnConfig3.setInfoPageLink (true); 
		tableConfig.addComponent(localColumnConfig3);

		return tableConfig;
	}

}
