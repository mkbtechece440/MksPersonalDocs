package ext.plural.builders;

import java.util.List;

import com.ptc.core.components.beans.ObjectBean;
import com.ptc.core.components.forms.DefaultObjectFormProcessor;
import com.ptc.core.components.forms.FormResult;
import com.ptc.netmarkets.util.beans.NmCommandBean;

import wt.util.WTException;

public class TableBuilderFormProcessor extends DefaultObjectFormProcessor {

	@Override
	public FormResult preProcess(NmCommandBean arg0, List<ObjectBean> arg1) throws WTException {
		// TODO Auto-generated method stub
		return super.preProcess(arg0, arg1);
	}

	@Override
	public FormResult doOperation(NmCommandBean arg0, List<ObjectBean> arg1) throws WTException {
		// TODO Auto-generated method stub
		return super.doOperation(arg0, arg1);
	}

	@Override
	public FormResult postProcess(NmCommandBean arg0, List<ObjectBean> arg1) throws WTException {
		// TODO Auto-generated method stub
		return super.postProcess(arg0, arg1);
	}

	@Override
	public FormResult postTransactionProcess(NmCommandBean arg0, List<ObjectBean> arg1) throws WTException {
		// TODO Auto-generated method stub
		return super.postTransactionProcess(arg0, arg1);
	}
	

}
