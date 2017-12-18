package ie.gmit.sw;

public class Request {
	private long taskId;
	private String query;
	
	public Request(long taskId, String query) {
		this.taskId = taskId;
		this.query = query;
	}
}
