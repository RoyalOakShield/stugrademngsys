import junit.framework.TestCase;
import com.lzl.Reply;

import org.json.*;

public class ReplyClassTest extends TestCase{
	public void testReplyClass(){
		//Test Reply
		JSONObject testJSONDoc=new JSONObject();
		testJSONDoc.put("Return",1);
		testJSONDoc.put("Content",new JSONArray().put(new JSONObject().put("Username","2014220420001")));
	
		Reply testReply=new Reply(testJSONDoc);
		testReply.setReturnValue(3);
		JSONArray referenceToContent=testReply.getContent();
		referenceToContent.put(new JSONObject().put("Username","2014220402029"));
		testReply.replaceContent(referenceToContent);

		System.out.println(testReply.toJSON());
	}
}
