import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote{
	public long add(long a, long b) throws RemoteException;
	public long sub(long a, long b) throws RemoteException;
	public long mul(long a, long b) throws RemoteException;
	public long div(long a, long b) throws RemoteException;
	public double var(long[] a) throws RemoteException;
	public long mean(long[] a) throws RemoteException;
	public double qua(int a, int b, int c) throws RemoteException;
}