package ie.gmit.sw.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String[] args) throws RemoteException {
		// Start the RMI registry on port 1099
		LocateRegistry.createRegistry(1099);

		// Print a message to standard output
		System.out.println("Server listening on port 1099...");
	}

}
