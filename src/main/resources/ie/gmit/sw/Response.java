package ie.gmit.sw;

import java.io.Serializable;
import java.util.List;

public class Response  implements Serializable {
	private static final long serialVersionUID = 42L;
	
	private long taskId;
	private List<String> defintions;
	
	public Response(long taskId, List<String> defintions) {
		super();
		this.taskId = taskId;
		this.defintions = defintions;
	}
	
	public List<String> getDefintions() {
		return defintions;
	}
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setDefintions(List<String> defintions) {
		this.defintions = defintions;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "Response [taskId=" + taskId + ", defintions=" + defintions + "]";
	}

}
