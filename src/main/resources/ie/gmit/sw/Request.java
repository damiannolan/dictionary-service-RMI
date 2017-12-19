package ie.gmit.sw;

import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = 42L;
	
	private long taskId;
	private String query;
	
	public Request(long taskId, String query) {
		this.taskId = taskId;
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "Request [taskId=" + taskId + ", query=" + query + "]";
	}
	
}
