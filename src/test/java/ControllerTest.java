import junit.framework.TestCase;
import com.lzl.controller.LzlControllerMain;

public class ControllerTest extends TestCase{
	public void testController(){
		new LzlControllerMain().start();
		try{
			Thread.sleep(5000000);
		}
		catch(Exception e){
			System.err.println(e);
		}
	}
}
