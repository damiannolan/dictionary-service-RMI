package ie.gmit.sw.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDictionaryService extends Remote {
	
	public String lookUp(String s) throws RemoteException;
}
