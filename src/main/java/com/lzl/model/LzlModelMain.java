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
	private MongoCollection<Document> studentAccounts,teacherAccounts,facultyAccounts,managerAccounts;  //students,teachers,facultymen and managers' login accounts
	private MongoCollection<Document> studentInfo,teacherInfo,courseInfo,scoreInfo,facultyInfo; //students, teachers ,courses, scores and faculotymen's personal information
	
	//Initialize the connection to MongoDB
	private void initializeDatabase(){
		//默认端口27017
		mongoClient=new MongoClient("localhost","27017"); 
		mongoDatabase=mongoClient.getDatabase("stugrademngsys");
		studentAccounts=mongoDatabase.getCollection("studentAccounts");
		teacherAccounts=mongoDatabase.getCollection("teacherAccounts");
		facultyAccounts=mongoDatabase.getCollection("facultyAccounts");
		managerAccounts=mongoDatabase.getCollection("managerAccounts");
		
	}
}
