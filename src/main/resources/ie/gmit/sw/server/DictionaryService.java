package ie.gmit.sw.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DictionaryService extends UnicastRemoteObject implements IDictionaryService {

	private static final long serialVersionUID = 42L;

	protected DictionaryService() throws RemoteException {
		super();
	}

	public String lookUp(String s) {
		System.out.println("Requested lookup");
		return "lookUp called remotely";
	}

}
