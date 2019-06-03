
import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.security.*;
import javax.net.ssl.*;


public class SServer implements Runnable {

	KeyStore ks=null;
	KeyManagerFactory kmf=null;
	SSLContext sc=null;
	String runRoot="C:/eclipse_workspace/networkP/finalProject/bin/";
	String ksName=runRoot+".keystore/SSLSocketServerKey";
	
	char keyStorePass[]="505322".toCharArray();
	char keyPass[]="505322".toCharArray();
	
	
	ChatServerRunnable clients[]=new ChatServerRunnable[3];
	public int clientCount=0;
	
	int sPort=-1;

	static SSLServerSocketFactory ssf=null;
	static SSLServerSocket s=null;
	static SSLSocket c=null;
	

	public SServer(int sPort) {
		this.sPort=sPort;
		
	}

	//run()
	public void run() {
		
		try {
			ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(ksName), keyStorePass);

			kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, keyPass);

			sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);

			ssf = sc.getServerSocketFactory();
			s = (SSLServerSocket) ssf.createServerSocket(sPort);
//			printServerSocketInfo(s);

			System.out.println ("Server started: socket created on " + sPort);



			while(true) {
				addClient(s);
			}
		}catch(SSLException se) {
		}catch(BindException b) {
			System.out.println("Can't bind on :"+sPort);
			
		}catch(IOException i){
			System.out.println(i);
		}catch(Exception e){
			System.out.println("What?? exit~~");
			System.out.println(e);
		}
		finally {
		
			try {
				if(s!=null)
					s.close();
			}catch(Exception i) {
				System.out.println(i);
			}
		}
	}

	public int whoClient(int clientID) {
		 for(int i=0;i<clientCount;i++) 
			 if(clients[i].getClientID()==clientID)
				 return i;
			 return -1;
	}
	
	
	public void putClient(int clientID,String inputLine) {
		for(int i=0;i<clientCount;i++)
			if(clients[i].getClientID()==clientID) {
				System.out.println("Writer :"+clientID);
			}else {
				System.out.println("write: "+clients[i].getClientID());
				clients[i].out.println(inputLine);
			}
	}
	
	
	public void addClient(SSLServerSocket s) {
		SSLSocket c=null;
		
		
		if(clientCount<clients.length) {
			try {
				c=(SSLSocket)s.accept();
				c.setSoTimeout(40000);
			}catch(IOException i) {
				System.out.println("Accept() fail: "+i);
			}
			clients[clientCount]=new ChatServerRunnable(c,this);
			new Thread(clients[clientCount]).start();
			clientCount++;
			System.out.println("Client connected : "+c.getPort()+", CurrentClient: "+clientCount);
			
		}else {
			try {
				SSLSocket dummySocket=(SSLSocket)s.accept();
				ChatServerRunnable dummyRunnable=new ChatServerRunnable(dummySocket,this);
				new Thread(dummyRunnable);
				dummyRunnable.out.println(dummySocket.getPort()+"<Sorry maximum user connected now");
				System.out.println("Client refused: maxumum connection "+clients.length+" reached");
				dummyRunnable.close();
			}catch(IOException i) {
				System.out.println(i);
			}
		}
	}
	public synchronized void delClient(int clientID) {
		int pos=whoClient(clientID);
		ChatServerRunnable endClient=null;
		
		if(pos>=0) {
			endClient=clients[pos];
			if(pos>=0) {
				endClient=clients[pos];
				if(pos<clientCount-1)
					for(int i=pos+1;i<clientCount;i++)
						clients[i-1]=clients[i];
				clientCount--;
				System.out.println("Client removed : "+clientID+"at clients["+pos+"], CurrentClient: "+clientCount);
				endClient.close();
			}
		}
	}
	public static void main(String[] args) throws IOException{
		if (args.length != 1) {
			System.out.println("Usage: Classname Port");
			System.exit(1);
		}
		int sPort=Integer.parseInt(args[0]);
		
		new Thread(new SServer(sPort)).start();
	}
//	private static void printSocketInfo(SSLSocket s) {
//		System.out.println("Socket class: "+s.getClass());
//		System.out.println("   Remote address = "
//				+s.getInetAddress().toString());
//		System.out.println("   Remote port = "+s.getPort());
//		System.out.println("   Local socket address = "
//				+s.getLocalSocketAddress().toString());
//		System.out.println("   Local address = "
//				+s.getLocalAddress().toString());
//		System.out.println("   Local port = "+s.getLocalPort());
//		System.out.println("   Need client authentication = "
//				+s.getNeedClientAuth());
//		SSLSession ss = s.getSession();
//		System.out.println("   Cipher suite = "+ss.getCipherSuite());
//		System.out.println("   Protocol = "+ss.getProtocol());
//	}
//	private static void printServerSocketInfo(SSLServerSocket s) {
//		System.out.println("Server socket class: "+s.getClass());
//		System.out.println("   Server address = "+s.getInetAddress().toString());
//		System.out.println("   Server port = "+s.getLocalPort());
//		System.out.println("   Need client authentication = "+s.getNeedClientAuth());
//		System.out.println("   Want client authentication = "+s.getWantClientAuth());
//		System.out.println("   Use client mode = "+s.getUseClientMode());
//	}
}


//-----------------chatserverRunnable------------------------//

class ChatServerRunnable implements Runnable{

	protected SSLSocket c=null;			
	protected PrintWriter out=null;
	protected BufferedReader in=null;
	public int clientID=-1;
	protected SServer server=null;

	public ChatServerRunnable(SSLSocket c,SServer server) {

		this.c=c;
		this.server=server;
		
		clientID=c.getPort();
		try {
			out=new PrintWriter(c.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(c.getInputStream()));
		}catch(IOException i) {
			
		}
	}
	//run
	public void run() {
		try {
			String inputLine;
			while((inputLine=in.readLine())!=null) {
				server.putClient(getClientID(),getClientID()+":"+inputLine);
				if(inputLine.equalsIgnoreCase("Bye"))
					break;
			}
			server.delClient(getClientID());
		}catch(SocketTimeoutException ste) {
			System.out.println("Socket timeout Occurred, force close():"+getClientID());
			server.delClient(getClientID());
		}catch(IOException e) {
			server.delClient(getClientID());
		}
	}
	

	public int getClientID() {
		return clientID;
	}

	public void close() {
		try {
			if(in!=null) {
				in.close(); //BufferedReader in
			}
			if(out!=null) {
				out.close(); //PrintWriter out
			}
			if(c!=null) {
				c.close();
			}
		}catch(IOException i) {
				
			}
		
	}
}
	
//---------------chatserverRunnable end-------------------------//


