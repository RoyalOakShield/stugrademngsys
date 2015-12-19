package com.lzl;

import org.json.*;

public class Request {
	private String request,identity,infotype;

	private JSONObject secondDetail;

	//构造函数
	public Request(){
		request=null;
		identity=null;
		infotype=null;
		secondDetail=null;
	}

	public Request(String req,String iden,String info){
		request=req;
		identity=iden;
		infotype=info;
		secondDetail=null;
	}

	public Request(String req,String iden,String info,JSONObject sec){
		request=req;
		identity=iden;
		infotype=info;
		secondDetail=sec;
	}

	public Request(JSONObject requestJSON){
		parseJSONRequest(requestJSON);
	}

	//parse JSON format request
	public void parseJSONRequest(JSONObject requestJSON){
		JSONObject firstDetail=(JSONObject)requestJSON.get("Detail");
		request=(String)requestJSON.get("Request");
		identity=(String)firstDetail.get("Identity");
		infotype=(String)firstDetail.get("Infotype");
		
		secondDetail=(JSONObject)firstDetail.get("Detail");
	}

	//getters
	public JSONObject getDetail(){
		return secondDetail;
	}

	public String getRequest(){
		return request;
	}
	
	public String getInfotype(){
		return infotype;
	}

	public String getIdentity(){
		return identity;
	}

	//setters
	public void setDetail(JSONObject sec){
		secondDetail=sec;
	}

	public void setRequest(String req){
		request=req;
	}
	
	public void setInfotype(String info){
		infotype=info;
	}

	public void setIdentity(String iden){
		identity=iden;
	}

	//convert to JSON format
	public JSONObject toJSON(){
		JSONObject requestJSONFormat=new JSONObject();
		requestJSONFormat.put("Request",request);

		JSONObject firstDetail=new JSONObject();
		firstDetail.put("Identity",identity);
		firstDetail.put("Infotype",infotype);
		firstDetail.put("Detail",secondDetail);
		
		requestJSONFormat.put("Detail",firstDetail);

		return requestJSONFormat;
	}
}
