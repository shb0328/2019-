
import java.io.*;
import java.security.*;
import javax.net.ssl.*;

public class SSLSocketServer {

	public static void main(String[] args) {
		
		final KeyStore ks;
		final KeyManagerFactory kmf;
		final SSLContext sc;
		
		final String runRoot = "C:/eclipse_workspace/networkP/realfinalproj/bin/";  // root change : your system root

		SSLServerSocketFactory ssf = null;
		SSLServerSocket s = null;
		SSLSocket c = null;
		
		BufferedWriter w = null;
		BufferedReader r = null;
		
		if (args.length != 1) {
			System.out.println("Usage: Classname Port");
			System.exit(1);
		}
		int sPort = Integer.parseInt(args[0]);
		
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
			
			/* SSLEngine
			sslEngine = sslContext.createSSLEngine();
			sslEngine.setUseClientMode(false);
			sslSession = sslEngine.getSession();
			
			dummy = ByteBuffer.allocate(0);
			outNetBuffer = ByteBuffer.allocate(this.getNetBufferSize());
			inAppBuffer = ByteBuffer.allocate(this.getAppBufferSize());
			*/
			
			/* SSLServerSocket */

			ssf = sc.getServerSocketFactory();
			s = (SSLServerSocket) ssf.createServerSocket(sPort);
			printServerSocketInfo(s);
			
			c = (SSLSocket) s.accept();
			printSocketInfo(c);
			
			w = new BufferedWriter( new OutputStreamWriter(c.getOutputStream()));
			r = new BufferedReader( new InputStreamReader(c.getInputStream()));
			
			String m = "Hello ^^ '진성우재RPG'에 오신 것을 환영합니다! 참여 인원이 2명이 되면, 자동으로 시작합니다. 잠시만 기다려주세요! exit '.'";
			
			Role sxxgxxnz = new Role(s.getLocalSocketAddress().toString(),1);
			
			
			w.write(m,0,m.length());
			w.newLine();
			w.flush();
			
			while((m=r.readLine())!=null) {
				if(m.equals(".")) break;
				char[] a = m.toCharArray();
				int n = a.length;
				for(int i =0; i<n/2; i++) {
					char t = a[i];
					a[i] = a[n-1-i];
					a[n-i-1] = t;
				}
				w.write(a,0,n);
				w.newLine();
				w.flush();
			}
			
		
			
			w.close(); //Buf writer
			r.close(); //Buf reader
			s.close(); //server socket
			c.close(); //socket
				
		} catch (SSLException se) {
			System.out.println("SSL problem, exit~");
			try {
				w.close();
				r.close();
				s.close();
				c.close();
			} catch (IOException i) {
			}
		} catch (Exception e) {
			System.out.println("What?? exit~");
			try {
				w.close();
				r.close();
				s.close();
				c.close();
			} catch (IOException i) {
			}
		}
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
}