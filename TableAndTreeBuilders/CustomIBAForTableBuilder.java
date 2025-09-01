package ext.plural.builders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ptc.core.components.descriptor.ModelContext;
import com.ptc.core.components.factory.dataUtilities.DefaultDataUtility;
import com.ptc.core.lwc.server.PersistableAdapter;
import com.ptc.core.meta.common.DisplayOperationIdentifier;

import wt.fc.Persistable;
import wt.session.SessionHelper;
import wt.util.WTException;

public class CustomIBAForTableBuilder extends DefaultDataUtility {
	
	private static final Logger logger = LogManager.getLogger(CustomIBAForTableBuilder.class);

	@Override
	public Object getDataValue(String componentId, Object datum, ModelContext modelContext) throws WTException {
		logger.debug("Entered into ext.plural.builders.getDataValue");
		String customAttributeValue = "empty";
		if(datum instanceof Persistable) {
			logger.debug("Entered into datum instanceof Persistable");
			Persistable persistable = (Persistable) datum;
			
			// Fetch the custom attribute value
            customAttributeValue = getStringIBAValue(persistable, "EXPERIENCE");
            logger.debug("customAttributeValue final: "+customAttributeValue);
		}
		return customAttributeValue;
	}
	
	private static String getStringIBAValue(Persistable persistable, String attrName) {
		logger.debug("ENTER : getStringIBAValue()");
		String attrValue = "Empty";
		PersistableAdapter persistableAdapter = null;
		try {
			persistableAdapter = new PersistableAdapter(persistable, null, SessionHelper.getLocale(),
					new DisplayOperationIdentifier());
			persistableAdapter.load(attrName);
			attrValue = (String) persistableAdapter.get(attrName);

		} catch (WTException e) {
			logger.error("Got error while getStringIBAValue for " + e);
		}
		logger.debug("EXIT : getStringIBAValue()");
		return attrValue;
	}
	
	

}
