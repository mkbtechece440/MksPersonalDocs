<%@ taglib uri="http://www.ptc.com/windchill/taglib/components" prefix="jca"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.ptc.com/windchill/taglib/carambola" prefix="cmb"%>
<%@ include file="/netmarkets/jsp/components/beginWizard.jspf"%>
<%@ include file="/netmarkets/jsp/components/includeWizBean.jspf"%>


<jca:wizard buttonList="DefaultWizardButtons" title="Table Builder and Tree Builder">
<jca:wizardStep action="ShowTableWizardStep" type="tableBuilder" label ="Table Builder" />
<jca:wizardStep action="ShowTreeWizardStep" type="tableBuilder" label ="Tree Builder" />
</jca:wizard>


<SCRIPT language=javascript>
alert('Welcome to Table/Tree Builders Example');
function validationCreateLocation(event){
	console.log('Welcome to Table/Tree Builders Example');
	alert('Welcome to Table/Tree Builders Example');
}
</SCRIPT>
<%@include file="/netmarkets/jsp/util/end.jspf"%>