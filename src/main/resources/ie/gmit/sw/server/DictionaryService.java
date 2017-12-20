package ie.gmit.sw.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryService extends UnicastRemoteObject implements IDictionaryService {

	private static final long serialVersionUID = 42L;
	private Map<String, List<String>> dictionary = new HashMap<String, List<String>>();

	public DictionaryService() throws RemoteException {
		super();
		// Create an empty dictionary
		this.dictionary = new HashMap<String, List<String>>();
	}

	public DictionaryService(String fileName) throws RemoteException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			StringBuilder def = new StringBuilder();
			
			// Skip first line
			reader.readLine();
			
			// Read line of text
			String next = reader.readLine();

			while (next != null) {
				if (next.charAt(0) == '"') { 
					String word = next.substring(1, next.indexOf('"', 1));
																			
					do {
						def.append(next + "\n");
						next = reader.readLine(); // move onto next one
					} while (next != null && next.charAt(0) != '"');

					addToDictionary(word.toLowerCase(), def.toString());
					def = new StringBuilder(); // clear string
				}
			}
			System.out.println("Finished loading dictionary");
			reader.close();
		} catch (IOException e) {
			// Create an empty dictionary
			System.out.println("Could not find file - " + fileName + ".\n Try placing the file in the same directory as your jar file or at the root level of your application");;
			this.dictionary = new HashMap<String, List<String>>();																	
		}
	}

	private void addToDictionary(String word, String defintion) {
		if (dictionary.get(word) == null) {
			List<String> defList = new ArrayList<String>();
			defList.add(defintion);

			dictionary.put(word, defList);
		} else {
			dictionary.get(word).add(defintion);
		}
	}
	
	private List<String> getDefintion(String word) {
		return dictionary.get(word.toLowerCase());
	}
	
	public List<String> lookUp(String s) {
		// Log
		System.out.println("[INFO] Query Request - " + s);
		
		
		List<String> defList = getDefintion(s);
		
		if(defList == null) {
			defList = new ArrayList<String>();
			defList.add("System could not find definition for - " + s);
		}
		return defList;
	}
}
