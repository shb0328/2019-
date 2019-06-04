import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator{
	
	private static final long serialVersionUID=1L;
	
	public CalculatorImpl() throws RemoteException{
		super();
	}
	public long add(long a, long b)throws RemoteException{
		return a+b;
	}
	public long sub(long a, long b)throws RemoteException{
		return a-b;
	}
	public long mul(long a, long b)throws RemoteException{
		return a*b;
	}
	public long div(long a, long b)throws RemoteException{
		return a/b;
	}
}