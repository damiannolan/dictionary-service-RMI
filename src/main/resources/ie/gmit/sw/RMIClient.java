package ie.gmit.sw;

public class RMIClient implements Runnable {

	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Running in another thread");
	}

}
