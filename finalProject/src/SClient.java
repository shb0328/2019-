
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.net.ssl.*;


class ClientSender implements Runnable{

	 static SSLSocket c=null;

	ClientSender(SSLSocket c){
		this.c=c;

	}
	public void run() {
		Scanner KeyIn=null;
		PrintWriter out=null;
		try {
			KeyIn=new Scanner(System.in);
			out=new PrintWriter(c.getOutputStream(),true);
			String userInput="";
			System.out.print("You are "+c.getLocalPort()+" , Type Message(\"Bye\" to leave)\n");
			System.out.println("혜빈 승은 RPG에 오신 것을 환영합니다!\n참가자가 두명이 되면 자동으로 시작합니다. 잠시만 기다려주세요.\n ====================== \n");
			while((userInput=KeyIn.nextLine())!=null) {
				out.println(userInput);
				out.flush();
				
				if(userInput.equalsIgnoreCase("Bye"))
					break;
			}
			KeyIn.close();
			out.close();
			c.close();
		}catch(IOException i) {
			try {
				if(out!=null) out.close();
				if(KeyIn!=null) KeyIn.close();
				if(c!=null) c.close();
			}catch(IOException e) {
				
			}
			System.exit(1);
		}
	}
}

class ClientReceiver implements Runnable{
	static SSLSocket c=null;

	
	ClientReceiver(SSLSocket c){
		this.c=c;

	}
	public void run() {

		while(c.isConnected()) {
			BufferedReader in=null;
			try {
				in=new BufferedReader(new InputStreamReader(c.getInputStream()));
				String readSome=null;
				while((readSome=in.readLine())!=null) {
					System.out.println(readSome);
				}
				in.close();
				c.close();
			}catch(IOException i) {
				try {
					if(in!=null) in.close();
					if(c!=null) c.close();
				}catch(IOException e) {
					
				}
				System.out.println("Leave. ");
				System.exit(1);
			}
		}
	}
	}


public class SClient {
	static String sServer="";
	static int sPort=0000;
	//static int clientID=-1;
	static SSLSocketFactory f=null;
	static SSLSocket c=null;
	static SSLSocket c2=null;
	
public static void main(String[] args) {
	
	if (args.length != 2) {
		System.out.println("Usage: Classname ServerName securePort");
		System.exit(1);
	}
	sServer = args[0];
	sPort = Integer.parseInt(args[1]);
	
	try {
		System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "505322");
		
		f=(SSLSocketFactory) SSLSocketFactory.getDefault();
		c=(SSLSocket) f.createSocket(sServer,sPort);
		
		String[] supported=c.getSupportedCipherSuites();
		c.setEnabledCipherSuites(supported);
		printSocketInfo(c);
		
	//	c.startHandshake();
		//clientID=c.getLocalPort();
		
	}catch(BindException b) {
		System.out.println("Can't bind on: "+sPort);
		System.exit(1);
	}catch(IOException i) {
		System.out.println(i);
		System.exit(1);
	}
	new Thread(new ClientReceiver(c)).start();
	new Thread(new ClientSender(c)).start();
	
}


public static void printSocketInfo(SSLSocket c) {
	System.out.println("Socket class: "+c.getClass());
	System.out.println("   Remote address = "
			+c.getInetAddress().toString());
	System.out.println("   Remote port = "+c.getPort());
	System.out.println("   Local socket address = "
			+c.getLocalSocketAddress().toString());
	System.out.println("   Local address = "
			+c.getLocalAddress().toString());
	System.out.println("   Local port = "+c.getLocalPort());
	System.out.println("   Need client authentication = "
			+c.getNeedClientAuth());
	SSLSession ss = c.getSession();
	
	System.out.println("   Cipher suite = "+ss.getCipherSuite());
	System.out.println("   Protocol = "+ss.getProtocol());
}
}
		
	

