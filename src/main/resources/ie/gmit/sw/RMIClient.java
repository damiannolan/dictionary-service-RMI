package ie.gmit.sw;

/*
 * Sleep for 10 seconds
 * Pull request from In Queue
 * Connect to RMI Registry
 * Make RMI Call
 * Add Response to Out Queue
 */
public class RMIClient implements Runnable {
	private Request request;
	
	public RMIClient(Request req) { 
		this.request = req;
	}
	
	public void run() {
		try {
			System.out.println("Starting RMI Client for: " + request);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
