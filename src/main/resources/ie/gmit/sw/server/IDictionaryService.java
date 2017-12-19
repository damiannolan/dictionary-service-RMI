package ie.gmit.sw.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDictionaryService extends Remote {
	
	public List<String> lookUp(String s) throws RemoteException;
}
