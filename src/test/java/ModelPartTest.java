import junit.framework.TestCase;
import com.lzl.model.LzlModelMain;
import org.json.*;
import java.net.*;
import com.lzl.NetworkTalker;

public class ModelPartTest extends TestCase {
	public void testModelPart(){
		new LzlModelMain().start();
		JSONObject testAddRequest=new JSONObject()
			.put("Request","GET")
			.put("Detail",new JSONObject()
					.put("Infotype","ACCOUNT")
					.put("Identity","STUDENT")
					.put("Detail",new JSONObject()
						.put("Username","9999")));

		System.out.println("Test request are:\n"+testAddRequest);

		JSONObject reply=null;
		try{
			Thread.sleep(2000);
			NetworkTalker talker=new NetworkTalker(6000,6001,6002,NetworkTalker.CONTROLLER);
			talker.sendRequest(NetworkTalker.MODEL,testAddRequest);
			reply=talker.getNextRequest();
		}
		catch(Exception e){
			System.err.println(e);
		}
		System.out.println("Reply:");
		System.out.println(reply.toString());
	}
}
