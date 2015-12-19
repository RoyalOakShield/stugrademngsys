import com.lzl.Request;
import junit.framework.TestCase;
import org.json.*;

public class RequestClassTest extends TestCase{
	public void testRequestToJSON(){
		Request req=new Request();
		JSONObject secondDetail=new JSONObject()
					.put("Username","2014220402001")
					.put("Password","123456789");
		req.setRequest("ADD");
		req.setIdentity("STUDENT");
		req.setInfotype("ACCOUNT");
		req.setDetail(secondDetail);

		JSONObject reqJSON=req.toJSON();
		System.out.println("REQUEST: ADD, IDENTITY: STUDENT, INFOTYPE: ACCOUNT\n");
		System.out.println(reqJSON.toString());

		Request testParseJSON=new Request(reqJSON);
		System.out.println(testParseJSON.getRequest()+" "+testParseJSON.getIdentity()+" "+testParseJSON.getInfotype());
		assertEquals("ADD",testParseJSON.getRequest());
		assertEquals("STUDENT",testParseJSON.getIdentity());
		assertEquals("ACCOUNT",testParseJSON.getInfotype());
	}
}
