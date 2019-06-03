
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.SocketTimeoutException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class SSLChatSocketServer implements Runnable{
	
	private SSLContext sslcontext;
	
	private SSLServerRunnable clients[] = new SSLServerRunnable[2];
	public int clientCount = 0;
	
	private int sPort = -1;
	
	public SSLChatSocketServer(int port) {
		this.sPort = port;
		
		final KeyStore ks;
		final KeyManagerFactory kmf;
		final SSLContext sc;
		
		final String runRoot = "C:/eclipse_workspace/networkP/realfinalproj/bin/";  // root change : your system root

//		SSLServerSocketFactory ssf = null;
//		SSLServerSocket s = null;
//		SSLSocket c = null;
		
//		BufferedWriter w = null;
//		BufferedReader r = null;

		
//		int sPort = Integer.parseInt(args[0]);
		
		String ksName = runRoot+".keystore/SSLSocketServerKey";
		
		char keyStorePass[] = "001004".toCharArray();
		char KeyPass[] = "001004".toCharArray();
		
		try {
			ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(ksName),keyStorePass);
			
			kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, KeyPass);
			
			sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			
			sslcontext = sc;
			
			/* SSLServerSocket */

//			ssf = sc.getServerSocketFactory();
//			s = (SSLServerSocket) ssf.createServerSocket(sPort);
//			printServerSocketInfo(s);
//			
//			c = (SSLSocket) s.accept();
//			printSocketInfo(c);
			
//			w = new BufferedWriter( new OutputStreamWriter(c.getOutputStream()));
//			r = new BufferedReader( new InputStreamReader(c.getInputStream()));
			
//			String m = "Hello ^^ '진성우재RPG'에 오신 것을 환영합니다! 참여 인원이 2명이 되면, 자동으로 시작합니다. 잠시만 기다려주세요! exit '.'";
			
//			Role sxxgxxnz = new Role(s.getLocalSocketAddress().toString(),1);
			
			
//			w.write(m,0,m.length());
//			w.newLine();
//			w.flush();
			
//			while((m=r.readLine())!=null) {
//				if(m.equals(".")) break;
//				char[] a = m.toCharArray();
//				int n = a.length;
//				for(int i =0; i<n/2; i++) {
//					char t = a[i];
//					a[i] = a[n-1-i];
//					a[n-i-1] = t;
//				}
//				w.write(a,0,n);
//				w.newLine();
//				w.flush();
//			}
//			
		
			
//			w.close(); //Buf writer
//			r.close(); //Buf reader
//			s.close(); //server socket
//			c.close(); //socket
				
		} catch (SSLException se) {
			System.out.println("SSL problem, exit~");
			
		} catch (Exception e) {
			System.out.println("What?? exit~");
	
		}
		
	}
	
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("Usage: Classname Port");
			System.exit(1);
		}
		
		int sPort = Integer.parseInt(args[0]);
		new Thread(new SSLChatSocketServer(sPort)).start();
		
	}
	
	private static void printSocketInfo(SSLSocket s) {
		System.out.println("Socket class: "+s.getClass());
		System.out.println("   Remote address = "
				+s.getInetAddress().toString());
		System.out.println("   Remote port = "+s.getPort());
		System.out.println("   Local socket address = "
				+s.getLocalSocketAddress().toString());
		System.out.println("   Local address = "
				+s.getLocalAddress().toString());
		System.out.println("   Local port = "+s.getLocalPort());
		System.out.println("   Need client authentication = "
				+s.getNeedClientAuth());
		SSLSession ss = s.getSession();
		System.out.println("   Cipher suite = "+ss.getCipherSuite());
		System.out.println("   Protocol = "+ss.getProtocol());
	}
	private static void printServerSocketInfo(SSLServerSocket s) {
		System.out.println("Server socket class: "+s.getClass());
		System.out.println("   Server address = "+s.getInetAddress().toString());
		System.out.println("   Server port = "+s.getLocalPort());
		System.out.println("   Need client authentication = "+s.getNeedClientAuth());
		System.out.println("   Want client authentication = "+s.getWantClientAuth());
		System.out.println("   Use client mode = "+s.getUseClientMode());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void run() {
		SSLServerSocketFactory ssf = null;
		SSLServerSocket s = null;
		
		ssf = sslcontext.getServerSocketFactory();
		
		try {
			s = (SSLServerSocket) ssf.createServerSocket(sPort);
			while (true) {
				addClient(s);
			}
		}catch (BindException b) {
			System.out.println("Can't bind on: "+sPort);
		} catch (IOException i) {
			System.out.println(i);
		} finally {
			try {
				if (s != null) s.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
//		printServerSocketInfo(s);
		

		try {
			
			System.out.println ("Server started: socket created on " + sPort);
			
			while (true) {
				addClient(s);
			}
		} finally {
			try {
				if (s != null) s.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}
	
	public int whoClient(int clientID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getClientID() == clientID)
				return i;
		return -1;
	}
	public void putClient(int clientID, String inputLine) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getClientID() == clientID) {
				System.out.println("writer: "+clientID);
			} else {
				System.out.println("write: "+clients[i].getClientID());
				clients[i].out.println(inputLine);
			}
	}
	public void addClient(SSLServerSocket serverSocket) {
		SSLSocket clientSocket = null;
		
		if (clientCount < clients.length) { 
			try {
				clientSocket = (SSLSocket) serverSocket.accept();
				printSocketInfo(clientSocket);
				clientSocket.setSoTimeout(40000); // 1000/sec
			} catch (IOException i) {
				System.out.println ("Accept() fail: "+i);
			}
			clients[clientCount] = new SSLServerRunnable(this, clientSocket);
			new Thread(clients[clientCount]).start();
			clientCount++;
			System.out.println ("Client connected: " + clientSocket.getPort()
					+", CurrentClient: " + clientCount);
		} else {
			try {
				SSLSocket dummySocket = (SSLSocket)serverSocket.accept();
				SSLServerRunnable dummyRunnable = new SSLServerRunnable(this, dummySocket);
				new Thread(dummyRunnable);
				dummyRunnable.out.println(dummySocket.getPort()
						+ " < Sorry maximum user connected now");
				System.out.println("Client refused: maximum connection "
						+ clients.length + " reached.");
				dummyRunnable.close();
			} catch (IOException i) {
				System.out.println(i);
			}	
		}
	}
	public synchronized void delClient(int clientID) {
		int pos = whoClient(clientID);
		SSLServerRunnable endClient = null;
	      if (pos >= 0) {
	    	   endClient = clients[pos];
	    	  if (pos < clientCount-1)
	    		  for (int i = pos+1; i < clientCount; i++)
	    			  clients[i-1] = clients[i];
	    	  clientCount--;
	    	  System.out.println("Client removed: " + clientID
	    			  + " at clients[" + pos +"], CurrentClient: " + clientCount);
	    	  endClient.close();
	      }
	}
	
}






class SSLServerRunnable implements Runnable {
	protected SSLChatSocketServer server = null;
	protected SSLSocket clientSocket = null;
	protected PrintWriter out = null;
	protected BufferedReader in = null;
	public int clientID = -1;
	
	public SSLServerRunnable (SSLChatSocketServer server, SSLSocket socket) {
		this.server = server;
		this.clientSocket = socket;
		clientID = clientSocket.getPort();
		try {
			out = new
					PrintWriter(clientSocket.getOutputStream(),true);
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
//			out = new BufferedWriter( new OutputStreamWriter(c.getOutputStream()));
//			in = new BufferedReader( new InputStreamReader(c.getInputStream()));
			String m = "Hello ^^ '진성우재RPG'에 오신 것을 환영합니다! 참여 인원이 2명이 되면, 자동으로 시작합니다. 잠시만 기다려주세요! exit '.'";
			
//			Role sxxgxxnz = new Role(s.getLocalSocketAddress().toString(),1);
			
			
			out.write(m,0,m.length());
//			out.newLine();
			out.flush();
//			out = new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()));
//			in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
		}catch(IOException i) {
			
		}
	}
	
	public void run() {
		try {
			String inputLine;
			while ((inputLine = in.readLine())!= null) {
				server.putClient(getClientID(),getClientID() + ":"+inputLine);
				if(inputLine.equalsIgnoreCase("Bye."))
					break;
			}
			server.delClient(getClientID());
		}catch(SocketTimeoutException ste) {
			System.out.println("Socket timeout Occurred, force close() :"+getClientID());
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
			if (in != null) in.close();
			if(out != null) out.close();
			if(clientSocket != null) clientSocket.close();
		}catch(IOException i) {
			
		}
	}
}
