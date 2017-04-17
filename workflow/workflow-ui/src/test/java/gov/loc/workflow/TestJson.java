package gov.loc.workflow;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class TestJson {
   String jsonString = "{\"processDefinitionList\":[{\"process-definition\":{\"id\":\"Workflow.receivecontent\",\"name\":\"receivecontent\",\"version\":\"1.0\",\"forms\":{},\"variables\":{\"resubmit\":\"Boolean\",\"doInventory\":\"Boolean\",\"server\":\"String\",\"bag\":\"gov.loc.workflow.Bag\",\"doMalwareScan\":\"Boolean\",\"doVerify\":\"Boolean\",\"doBagInPlace\":\"Boolean\",\"doExport\":\"Boolean\",\"bagRequestBody\":\"String\",\"numberOfCopies\":\"String\",\"bagExistsRequestResponse\":\"String\",\"hostServer\":\"String\",\"doCopy\":\"Boolean\",\"bagExists\":\"String\",\"doDeleteFromStaging\":\"Boolean\",\"bagId\":\"String\",\"doWriteBagInfo\":\"Boolean\"},\"package-name\":\"gov.loc.workflow\",\"deployment-id\":\"loc-repository:workflow:1.0.0-SNAPSHOT\"}},{\"process-definition\":{\"id\":\"conditionalprocess\",\"name\":\"Conditional Process\",\"version\":\"\",\"forms\":{},\"variables\":{\"printA\":\"java.lang.String\"},\"package-name\":\"org.drools.bpmn2\",\"deployment-id\":\"loc-repository:workflow:1.0.0-SNAPSHOT\"}},{\"process-definition\":{\"id\":\"restpostprocess\",\"name\":\"Post via REST API\",\"version\":\"\",\"forms\":{},\"variables\":{\"NoteKey\":\"java.lang.String\",\"NoteMessage\":\"java.lang.String\",\"NoteJson\":\"java.lang.String\"},\"package-name\":\"org.drools.bpmn2\",\"deployment-id\":\"loc-repository:workflow:1.0.0-SNAPSHOT\"}},{\"process-definition\":{\"id\":\"restputprocess\",\"name\":\"Put via REST API\",\"version\":\"\",\"forms\":{},\"variables\":{\"result\":\"java.lang.String\"},\"package-name\":\"org.drools.bpmn2\",\"deployment-id\":\"loc-repository:workflow:1.0.0-SNAPSHOT\"}}]}";
   
   @Test
   public void test(){
	   //java.lang.System.out.println(jsonString);
	   
	   JSONObject json = new JSONObject(jsonString);
	   JSONArray array = json.getJSONArray("processDefinitionList");
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsn = array.getJSONObject(i);
			//java.lang.System.out.println(jsn.get("process-definition"));
			JSONObject pd = jsn.getJSONObject("process-definition");
			
			java.lang.System.out.println(pd.get("id"));
			java.lang.System.out.println(pd.get("name"));
			java.lang.System.out.println(pd.get("deployment-id"));
			
		}
   }
   
   
}
