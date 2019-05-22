import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
	//for Calculator's serialize warning,
	//keep serialVersionUID field for class version number
	private static final long serialVersionUID = 1L;
	
	public CalculatorImpl() throws RemoteException {
		super();
	}
	public long add(long a, long b) throws RemoteException {
		return a + b;
	}
	public long sub(long a, long b) throws RemoteException {
		return a- b;
	}
	public long mul(long a, long b) throws RemoteException {
		return a*b;
	}
	public long div(long a, long b) throws RemoteException {
		return a/b;
	}
	public double var(long[] a) throws RemoteException {
		long avg = mean(a);
		long[] arr = new long[a.length];
		for(int i =0; i< a.length; ++i) {
			arr[i] = (long)Math.pow(a[i] - avg,2);
		}
		return mean(arr);
		
	}
	public long mean(long[] a) throws RemoteException {
		long res = 0;
		for(int i =0; i< a.length; ++i) {
			res += a[i];
		}
		res /= a.length;
		return res;
	}
	public double qua(int a, int b, int c) throws RemoteException {
		double res;
		//lolol
		res = 1234;
		return res;
	}
}