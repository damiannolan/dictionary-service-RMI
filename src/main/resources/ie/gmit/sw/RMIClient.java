package ie.gmit.sw;

import java.rmi.Naming;

import ie.gmit.sw.server.IDictionaryService;

/*
 * Sleep for 10 seconds
 * Connect to RMI Registry
 * Make RMI Call
 * Add Response to Out Queue
 */
public class RMIClient implements Runnable {
	private final static String RMI_URL = "rmi://127.0.0.1:1099/dictionaryService";
	private IDictionaryService dictionary;
	private Request request;
	private OutQueueService outQueueService;
	
	public RMIClient(Request req) { 
		this.request = req;
	}
	
	public void run() {
		try {
			System.out.println("Starting RMI Client for: " + request);
			Thread.sleep(10000);
			
			// Get a handle on the remote interface implementation stored in the RMI Registry
			// via Naming.lookup()
			dictionary = (IDictionaryService) Naming.lookup(RMI_URL);
			Response resp = new Response(this.request.getTaskId(), dictionary.lookUp(request.getQuery()));
			
			this.outQueueService = OutQueueService.getInstance();
			outQueueService.queueResponse(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
