package com.lzl;

import org.json.*;

public class Reply {
	protected int return_value;
	protected JSONArray content;

	//controctors
	public Reply(){
		return_value=0;
		content=null;
	}

	public Reply(int rtn){
		return_value=rtn;
		content=null;
	}

	public Reply(int rtn,JSONArray array){
		return_value=rtn;
		content=new JSONArray();
		for(int i=0;i<array.length();i++){
			content.put(array.get(i));
		}
	}
	public Reply(JSONObject jsonReply){
		content=null;
		return_value=jsonReply.getInt("Return");
		if(jsonReply.has("Content")){
			content=jsonReply.getJSONArray("Content");
		}
	}
	
	//setters and getters
	public void setReturnValue(int rtn){
		return_value=rtn;
	}
	public int getReturnValue(){
		return return_value;
	}

	public void newContent(){
		content=new JSONArray();
	}
	public void dropContent(){
		content=null;
	}
	public void replaceContent(JSONArray array){
		content=new JSONArray();
		for(int i=0;i<array.length();i++){
			content.put(array.get(i));
		}
	}
	public boolean hasContent(){
		return content==null;
	}
	public JSONArray getContent(){
		return content;
	}

	
	public JSONObject toJSON(){
		JSONObject jsonDoc=new JSONObject();
		jsonDoc.put("Return",return_value);
		jsonDoc.put("Content",content);
		return jsonDoc;
	}
}
