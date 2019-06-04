
import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.*;
import javax.net.ssl.*;


public class SServer implements Runnable {

   KeyStore ks=null;
   KeyManagerFactory kmf=null;
   SSLContext sc=null;
   String runRoot="C:/Users/tmddms2292/eclipse-workspace/networking/finalProject/bin/";
   String ksName=runRoot+".keystore/SSLSocketServerKey";
   
   char keyStorePass[]="505322".toCharArray();
   char keyPass[]="505322".toCharArray();
   
   
   ChatServerRunnable clients[]=new ChatServerRunnable[3];
   public int clientCount=0;
   
   int sPort=-1;

   static SSLServerSocketFactory ssf=null;
   static SSLServerSocket s=null;
   static SSLSocket c=null;
   
   Calculator calc;
   
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
//         printServerSocketInfo(s);
         calc=new CalculatorImpl();
        Naming.rebind("rmi://127.0.0.1:1099/myserver",calc);
         System.out.println ("Server started: socket created on " + sPort);

         
         while(true) {
            addClient(s);           
         }
      }catch(SSLException se) {
    	  System.out.println("SSL problem,exit~");
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
            if(i == 0)
               clients[i].out.println("Çýºó : "+inputLine);
            else 
               clients[i].out.println("½ÂÀº : "+inputLine);

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
//	   ServerSocketChannel serverSocketChannel=null;
      if (args.length != 1) {
         System.out.println("Usage: Classname Port");
         System.exit(1);
      }
      int sPort=Integer.parseInt(args[0]);    
      new Thread(new SServer(sPort)).start();
   }

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
            server.putClient(getClientID(),inputLine);
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

