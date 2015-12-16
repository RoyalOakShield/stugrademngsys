package com.lzl.model;

import com.lzl.NetworkTalker;
import org.json.*;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.event.*;
import org.bson.*;
import java.io.IOException;

public class LzlModelMain extends Thread{
	/* 本后端程序只连接一个MongoDB后端和只使用一个MongoDB数据库，
		所以mongoClient和mongoDatabase 均只有一个*/
	private MongoClient mongoClient; // MongoDB客户端
	private MongoDatabase mongoDatabase; //数据库部分
	/* 可能存在多个Collection ,分管登陆帐号和成绩等 */

  	//students,teachers,facultymen and managers' login accounts
	private MongoCollection<Document> studentAccounts,teacherAccounts,facultyAccounts,managerAccounts;

	//students, teachers ,courses, managers and faculotymen's personal information
	private MongoCollection<Document> studentInfo,teacherInfo,courseInfo,facultyInfo,managerInfo; 

	//score information
	private MongoCollection<Document> score;

	//Communication component to controllor
	private NetworkTalker talker; 
	
	//nessary thins to build up a reply 
	private JSONObject reply;
	private JSONArray replyContents;
	private int count=0;
	private String identity;
	private String infotype;

	public LzlModelMain(){
		initializeDatabase();
	}
	//Initialize the connection to MongoDB
	private void initializeDatabase(){
		//默认端口27017
		mongoClient=new MongoClient("localhost",27017); 
		mongoDatabase=mongoClient.getDatabase("stugrademngsys");

		studentAccounts=mongoDatabase.getCollection("studentAccounts");
		teacherAccounts=mongoDatabase.getCollection("teacherAccounts");
		facultyAccounts=mongoDatabase.getCollection("facultyAccounts");
		managerAccounts=mongoDatabase.getCollection("managerAccounts");

		studentInfo=mongoDatabase.getCollection("studentInfo");
		teacherInfo=mongoDatabase.getCollection("teacherInfo");
		facultyInfo=mongoDatabase.getCollection("facultyInfo");
		managerInfo=mongoDatabase.getCollection("managerInfo");
		courseInfo=mongoDatabase.getCollection("courseInfo");

		score=mongoDatabase.getCollection("score");

		try{
			talker=new NetworkTalker(6000,6001,6002,NetworkTalker.MODEL);
		}
		catch(IOException e){
			System.err.println("IOException occured while creating NetworkTalker: "+e.toString());
		}
	}

	//main loop
	public void run(){
		while(true){
			//this program accepts exact one request each time
			//no more
			JSONObject request=new JSONObject();
			try{
				request=talker.getNextRequest();
			}
			catch(IOException e){
				System.err.println("IOException occured while receiving new request: "+e.toString());
			}
						//DEBUG
						System.out.println(request);
						//~DEBUG
			String action=(String)request.get("Request");//get to know what action the request want
			JSONObject firstDetail=(JSONObject)request.get("Detail"); // get the value of first "Detial" key to convenient next stages

			//do some common work for all situations
			identity=(String)firstDetail.get("Identity");
			infotype=(String)firstDetail.get("Infotype");
			JSONObject secondDetail=(JSONObject)firstDetail.get("Detail");
			Document searchReqDoc=Document.parse(secondDetail.toString());
			
			//nessary thins to build up a reply 
			reply=new JSONObject();
			replyContents=new JSONArray();
			count=0;

			//Work due to different situations
			if(action.equals("ADD")){
				addForkingCode(identity,infotype,searchReqDoc);
			}
			else if(action.equals("DEL")){}
			else if(action.equals("SET")){}
			else if(action.equals("GET")){
				//Query from the database
				FindIterable<Document> result=getForkingCode(identity,infotype,searchReqDoc);
				//Build up reply 
				result.forEach(new Block<Document>(){
					public void apply(final Document document){
						count++;
						//DEBUG
						System.out.println(document);
						//~DEBUG
						replyContents.put(new JSONObject()
									.put("Identity",identity)
									.put("Infotype",infotype)
									.put("Detail",new JSONObject(document.toJson()))
								);
					}
				});

				reply.put("Content",replyContents);
			}
			//Send back the result
			reply.put("Reply",count);

			try{
				talker.sendRequest(NetworkTalker.CONTROLLER,reply);
			}
			catch(IOException e){
				System.err.println("IOException occured while sending new request: "+e.toString());
			}
		}
	}
	
	//"GET" request action's forking code
	private FindIterable<Document> getForkingCode(String IDENTITY,String INFOTYPE,Document searchReqDoc){
			FindIterable<Document> result=null;
			if (INFOTYPE.equals("GRADE")){
				result=score.find(searchReqDoc);
			}
			else if(IDENTITY.equals("STUDENT") && INFOTYPE.equals("PERSONALINFO")){
				result=studentInfo.find(searchReqDoc);
			}
			else if(IDENTITY.equals("TEACHER") && INFOTYPE.equals("PERSONALINFO")){
				result=teacherInfo.find(searchReqDoc);
			}
			else if(IDENTITY.equals("FACULTY") && INFOTYPE.equals("PERSONALINFO")){
				result=facultyInfo.find(searchReqDoc);
			}
			else if(IDENTITY.equals("MANAGER") && INFOTYPE.equals("PERSONALINFO")){
				result=managerInfo.find(searchReqDoc);
			}
			else if(IDENTITY.equals("STUDENT") && INFOTYPE.equals("ACCOUNT")){
				result=studentAccounts.find(searchReqDoc);
			}
			else if(IDENTITY.equals("TEACHER") && INFOTYPE.equals("ACCOUNT")){
				result=teacherAccounts.find(searchReqDoc);
			}
			else if(IDENTITY.equals("FACULTY") && INFOTYPE.equals("ACCOUNT")){
				result=facultyAccounts.find(searchReqDoc);
			}
			else if(IDENTITY.equals("MANAGER") && INFOTYPE.equals("ACCOUNT")){
				result=managerAccounts.find(searchReqDoc);
			}
			
			return result;
	}

	//Notice: searchReqDoc does not mean that it is for search,just because it is convenient.
	private void addForkingCode(String IDENTITY,String INFOTYPE,Document searchReqDoc){

		if (INFOTYPE.equals("GRADE")){
			score.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("STUDENT") && INFOTYPE.equals("PERSONALINFO")){
			studentInfo.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("TEACHER") && INFOTYPE.equals("PERSONALINFO")){
			teacherInfo.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("FACULTY") && INFOTYPE.equals("PERSONALINFO")){
			facultyInfo.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("MANAGER") && INFOTYPE.equals("PERSONALINFO")){
			managerInfo.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("STUDENT") && INFOTYPE.equals("ACCOUNT")){
			studentAccounts.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("TEACHER") && INFOTYPE.equals("ACCOUNT")){
			teacherAccounts.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("FACULTY") && INFOTYPE.equals("ACCOUNT")){
			facultyAccounts.insertOne(searchReqDoc);
		}
		else if(IDENTITY.equals("MANAGER") && INFOTYPE.equals("ACCOUNT")){
			managerAccounts.insertOne(searchReqDoc);
		}

	}
}
