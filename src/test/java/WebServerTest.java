import com.lzl.view.LzlWebServer;

import junit.framework.TestCase;

public class WebServerTest extends TestCase{
	public void testWebServer(){
		LzlWebServer server=null;
		try{
			server=new LzlWebServer();
			server.start();
		}
		catch(Exception e){
			System.err.println(e);
		}

		try{
			Thread.sleep(50000);
		}
		catch(Exception e){
			System.err.println(e);
		}
		server.stop();
	}
}
