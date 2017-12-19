package ie.gmit.sw.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

	public static void main(String[] args) throws Exception {
		// Start the RMI registry on port 1099
		LocateRegistry.createRegistry(1099);
		
		// Bind a new DictionaryService obj to the registry with a human-readable name
		Naming.rebind("dictionaryService", new DictionaryService());

		// Print a message to standard output
		System.out.println("Server listening on port 1099...");
	}

}
