package com.lzl.commulib;

import java.net.*;
import org.json.*;

public class NetworkTalker{
	private static String address="127.0.0.1";
	private DatagramSocket socket;
	private InetAddress localHost;

	private int[] port_numbers; //Port numbers of three parts. Format: [View_port,Controller_port,Model_port]

	//Charactor code
	public static final int VIEW=0;
	public static final int CONTROLLER=1;
	public static final int MODEL=2;

	public NetworkTalker(int Vport,int Cport,int Mport,int characFlag){
		port_numbers=new int[3];
		port_numbers[this.VIEW]=Vport;
		port_numbers[this.CONTROLLER]=Cport;
		port_numbers[this.MODEL]=Mport;

		socket=new DatagramSocket(port_numbers[characFlag]);
		try{
			localHost=InetAddress.getLocalHost();
		}
		catch(UnknownHostException e){
			System.err.println(e);
			System.exit(-1);
		}
	}
	
	public void sendRequest(int des,JSONObject request){
		byte[] buf=request.toString().getBytes();
		DatagramPackage dp=new DatagramPackage(buf,buf.length,localHost,this.port_numbers[des]);
		socket.send(dp);
	}

	public JSONObject getNextRequest(){
		byte[] buf=new byte[2048];
		DatagramPackage dp=new DatagramPackage(buf,buf.length);
		socket.receive(dp);
		String msg=new String(dp.getData(),0,dp.getLength());
		return new JSONObject(msg);
	}
	
	public void close(){
		socket.close();
	}
}
