package com.lzl.model;

import com.lzl.NetworkTalker;
import org.json.*;
import com.mongodb.*;
import com.mongodb.event.*;
import org.bson.*;

public class LzlModelMain extends Thread{
	/* 本后端程序只连接一个MongoDB后端和只使用一个MongoDB数据库，
		所以mongoClient和mongoDatabase 均只有一个*/
	private MongoClient mongoClient; // MongoDB客户端
	private MongoDatabase mongoDatabase; //数据库部分
	/* 可能存在多个Collection ,分管登陆帐号和成绩等 */

  	//students,teachers,facultymen and managers' login accounts
	private MongoCollection<Document> studentAccounts,teacherAccounts,facultyAccounts,managerAccounts;

	//students, teachers ,courses, scores and faculotymen's personal information
	private MongoCollection<Document> studentInfo,teacherInfo,courseInfo,scoreInfo,facultyInfo; 

	//Communication component to controllor
	private NetworkTalker talker; 
	
	//Initialize the connection to MongoDB
	private void initializeDatabase(){
		//默认端口27017
		mongoClient=new MongoClient("localhost","27017"); 
		mongoDatabase=mongoClient.getDatabase("stugrademngsys");

		studentAccounts=mongoDatabase.getCollection("studentAccounts");
		teacherAccounts=mongoDatabase.getCollection("teacherAccounts");
		facultyAccounts=mongoDatabase.getCollection("facultyAccounts");
		managerAccounts=mongoDatabase.getCollection("managerAccounts");

		studentInfo=mongoDatabase.getCollection("studentInfo");
		teacherInfo=mongoDatabase.getCollection("teacherInfo");
		facultyInfo=mongoDatabase.getCollection("facultyInfo");
		managerInfo=mongoDatabase.getCollection("managerInfo");

		talker=new NetworkTalker(/*Vport*/,/*Cport*/,/*Mport*/,NetworkTalker.MODEL);
	}

	//main loop
	public void run(){
		while(1){
			//this program accepts exact one request each time
			//no more
			JSONObject request=talker.getNextRequest();
			String action=(String)request.get("Request");//get to know what action the request want
			JSONObject firstDetail=(JSONObject)request.get("Detail"); // get the value of first "Detial" key to convenient next stages
			if(action.equals("ADD")){}
			else if(action.equals("DEL")){}
			else if(action.equals("SET")){}
			else if(action.equals("GET")){
				String identity=(String)firstDetail.get("Identity");
				String infotype=(String)firstDetail.get("Infotype");
				JSONObject secondDetail=(JSONObject)firstDetail.get("Detail");
				
			}
		}
	}
}
