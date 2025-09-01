package ext.plural.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.ptc.core.components.beans.TreeHandlerAdapter;

import wt.change2.Changeable2;
import wt.change2.WTChangeOrder2;
import wt.doc.WTDocument;
import wt.doc.WTDocumentUsageLink;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.part.WTPartHelper;
import wt.part.WTPartMaster;
import wt.part.WTPartReferenceLink;
import wt.part.WTPartUsageLink;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.RelationalExpression;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.vc.VersionControlHelper;

public class CustomTreeHandlerAdapter extends TreeHandlerAdapter {

	private static final Logger LOGGER = LogR.getLoggerInternal(CustomTreeHandlerAdapter.class.getName());

	@Override
	public Map<Object, List> getNodes(List paramList) throws WTException {
		final Map<Object, List> nodes = new HashMap<Object, List>();
		System.out.println("Inside getNodes() of customTreeBuilderHelper class!!");

		List list = new ArrayList();
		Persistable per;
		LOGGER.debug("List size : " + paramList.size());
		/*
		 * Fetching child of the given parent.
		 */
		for (ListIterator localListIterator = paramList.listIterator(); localListIterator.hasNext(); nodes.put(per, list)) {
			// LOGGER.debug("The value is: " + localListIterator);
			per = (Persistable) localListIterator.next();
			// LOGGER.debug("The :: " + per);
			// LOGGER.debug(per.getClass() + " : Name of the class ");
			list = (getProcess(per));
			// LOGGER.debug(" The value of the list :: " + list);
		}
		LOGGER.debug("Final Value of Nodes : " + nodes);
		return nodes;
	}

	public List<Object> getRootNodes() throws WTException {
		final List<Object> rootNodes = new ArrayList<Object>();
		System.out.println("Inside getRootNodes() of customTreeBuilderHelper class!!");
		WTPart part = null;
		Set<WTPart> findingsList = new HashSet<WTPart>();
		String[] linkedFindingNumber = {"EP-010920251147", "EP-010920251149", "EP-010920251150"};

		QuerySpec qSpec = new QuerySpec(WTPart.class);
		ClassAttribute numberId = new ClassAttribute(WTPart.class, WTPart.NUMBER);
		qSpec.appendWhere(new SearchCondition(numberId, SearchCondition.IN, (RelationalExpression) new ArrayExpression(linkedFindingNumber)),
				new int[] { 0 });
		QueryResult queryResultOfFindings = PersistenceHelper.manager.find(qSpec);
		LOGGER.debug("Got queryResultOfFindings size: " + queryResultOfFindings.size());

		while (queryResultOfFindings.hasMoreElements()) {
			part = (WTPart) queryResultOfFindings.nextElement();
			part = (WTPart) VersionControlHelper.service.allIterationsOf(part.getMaster()).nextElement();

			findingsList.add(part);
		}

		rootNodes.addAll(findingsList);
		return rootNodes;
	}

	private List getProcess(Persistable per) {
		List<Persistable> persist = new ArrayList<Persistable>();
		System.out.println("Inside getgetProcess() of customTreeBuilderHelper class!!");

		if (per instanceof WTPart) {
			QueryResult queryResult = null;
			// getting the child parts
			try {
				queryResult = WTPartHelper.service.getUsesWTPartMasters((WTPart) per);
				while (queryResult.hasMoreElements()) {
					WTPartUsageLink ul = (WTPartUsageLink) queryResult.nextElement();
					WTPartMaster childPart = (WTPartMaster) ul.getUses();
					persist.add(childPart);
				}
			} catch (WTException exception) {
				exception.printStackTrace();
			}
			WTPart parentPart = (WTPart) per;
			// getting described by document
			try {
				queryResult = PersistenceHelper.manager.navigate(parentPart, WTPartDescribeLink.DESCRIBED_BY_ROLE, WTPartDescribeLink.class, true);

				while (queryResult.hasMoreElements()) {
					WTDocument linkedDoc = (WTDocument) queryResult.nextElement();
					persist.add(linkedDoc);
				}

			} catch (WTException exception) {
				exception.printStackTrace();
			}
			WTPart parentPart1 = (WTPart) per;
			// getting ChangeNotices
			try {
				queryResult = com.ptc.windchill.enterprise.change2.commands.RelatedChangesQueryCommands
						.getRelatedAffectingChangeNotices((Changeable2) parentPart1);
				while (queryResult.hasMoreElements()) {
					WTChangeOrder2 cn = (WTChangeOrder2) queryResult.nextElement();
					persist.add(cn);
				}
			} catch (WTException e) {
				e.printStackTrace();
			}
			// getting reference by documents
			WTPart parentPart2 = (WTPart) per;

			try {

				//System.out.println(p1.getIdentity()+"."+p1.getVersionIdentifier());
				QueryResult referencesDocs = PersistenceHelper.manager.navigate(parentPart2, WTPartReferenceLink.ROLE_BOBJECT_ROLE,
						WTPartReferenceLink.class, true);
				QueryResult referencesDocuments = PersistenceHelper.manager.navigate(parentPart2, WTDocumentUsageLink.USES_ROLE,
						WTDocumentUsageLink.class, true);

				System.out.println("size of the referenceDocs : " + referencesDocuments.size());
				while (referencesDocuments.hasMoreElements()) {
					WTDocument refDoc = (WTDocument) referencesDocuments.nextElement();
					System.out.println("refDoc" + refDoc.getName());

					persist.add(refDoc);
				}
			} catch (WTException e) {
				e.printStackTrace();
			}

		}
		//LOGGER.debug("List of Persistable: " + persist);
		System.out.println("List Of Persistables :" + persist);
		return persist;
	}

}
