import java.io.IOException;
import java.net.InetSocketAddress;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;

import org.json.*;
import com.lzl.NetworkTalker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

public class LzlWebServer(){
	//Web server instance
	protected HttpServer server;
	
	//Contructor
	public LzlWebServer() throws IOException{
		server=HttpServer.create(new InetSocketAddress(8000),0);
		server.createContext("/reqrly",new RequestReplyHandler());
		server.createContext("/resource/picture",new PictureHandler());
		server.setExecutor(null);
	}

	public void start(){
		server.start();
	}
	public void stop(){
		server.stop(2);
	}

	static class RequestReplyHandler implements HttpHandler{
		protected NetworkTalker talker;
		public RequestReplyHandler(){
			talker=new NetworkTalker(6000,6001,6002,NetworkTalker.VIEW);
		}

		public void handle(HttpExchange t) throws IOException{
			//get request from browser side
			BufferedInputStream requestStream=new BufferedInputStream(t.getRequestBody());
			byte[] requestContent=new byte[requestStream.available()];
			requestStream.read(requestContent,0,requestContent.length);
			requestStream.close();
			//Convert bytes to JSONObject
			JSONObject requestJSON=new JSONObject(new String(requestContent));
			//Send request to controller
			talker.sendRequest(NetworkTalker.CONTROLLER,requestJSON);
			//Receive reply from controller
			JSONObject replyJSON=talker.getNextRequest();
			//Convert JSON to String
			String replyContent=replyJSON.toString();
			//Send back to browser
			//Write headers
			Headers h=t.getResponseHeaders();
			h.add("Content-Type","application/json");
			t.sendResponseHeaders(200,replyContent.length());
			DataOutputStream replyStream=new DataOutputStream(t.getResponseBody());
			replyStream.writeBytes(replyContent);
			replyStream.close();
		}
	}
}
