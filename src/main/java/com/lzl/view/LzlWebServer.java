package com.lzl.view;

import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URI;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;

import org.json.*;
import com.lzl.NetworkTalker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

public class LzlWebServer{
	//Web server instance
	protected HttpServer server;
	
	//Contructor
	public LzlWebServer() throws IOException{
		server=HttpServer.create(new InetSocketAddress(8000),0);
		server.createContext("/reqrly",new RequestReplyHandler());
		server.createContext("/resource/picture",new PictureHandler());
		server.createContext("/",new HtmlPageHandler());
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
			try{
				talker=new NetworkTalker(6000,6001,6002,NetworkTalker.VIEW);
			}
			catch(Exception e){
				System.err.println(e);
			}
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

	static class PictureHandler implements HttpHandler{
		public void handle(HttpExchange t) throws IOException{
			//Get the file path
			String picPath=t.getRequestURI().toString();
			//Read data from file
			FileInputStream picFileStream=new FileInputStream(PictureHandler.class.getResource(picPath).getFile());
			byte[] picBuf=new byte[picFileStream.available()];
			picFileStream.read(picBuf,0,picBuf.length);
			picFileStream.close();
			//set response content-type and reply head
			Headers h=t.getResponseHeaders();
			h.add("Content-Type","image/png");
			t.sendResponseHeaders(200,picBuf.length);
			//Write image content into http response
			DataOutputStream responseStream=new DataOutputStream(t.getResponseBody());
			responseStream.write(picBuf,0,picBuf.length);
			responseStream.close();
		}
	}

	static class HtmlPageHandler implements HttpHandler{
		public void handle(HttpExchange t) throws IOException{
			//Extract file name with slash in the front
			String requestHtml=t.getRequestURI().toString();
			//Read data from file
			FileInputStream htmlFileStream=new FileInputStream(PictureHandler.class.getResource("/htmls"+requestHtml).getFile());
			byte[] htmlBuf=new byte[htmlFileStream.available()];
			htmlFileStream.read(htmlBuf,0,htmlBuf.length);
			htmlFileStream.close();
			//set response content-type and reply head
			Headers h=t.getResponseHeaders();
			h.add("Content-Type","text/html");
			t.sendResponseHeaders(200,htmlBuf.length);
			//Write image content into http response
			DataOutputStream responseStream=new DataOutputStream(t.getResponseBody());
			responseStream.write(htmlBuf,0,htmlBuf.length);
			responseStream.close();
		}
	}
}
