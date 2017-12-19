package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PollingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OutQueueService outQueueService;

	public PollingServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		try {		
			// Instantiate the OutQueueService
			this.outQueueService = new OutQueueService();
			outQueueService.consumeResponse();

		} catch (Exception e) {
			System.out.println("Queue Error occurred with RabbitMQ");
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		String taskNumber = request.getParameter("frmTaskNumber");
		int counter = 1;
		if (request.getParameter("counter") != null){
			counter = Integer.parseInt(request.getParameter("counter"));
			counter++;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// HTML, head and body tags
		out.print("<html><head><title>Dictionary Service</title>");
		out.print(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css\" />");
		out.print("</head>");
		out.print("<body>");
		out.print("<div class=\"container\">");
		out.print("<h2 class=\"text-center\">Dictionary Service</h2>");
		
		Response resp = outQueueService.pollResponse(Long.parseLong(taskNumber));
		System.out.println("Printing from Servlet ---- " + resp);
		if(resp == null) {
			out.print("<p id=\"waiting\" class=\"text-center\">Waiting for response...</p>");
			
			out.print("<form name=\"frmRequestDetails\" action=\"PollingServlet\">");
			out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
			out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
			out.print("</form>");
		} else {
			out.print("<div id=\"response\" class=\"text-center\"><h4 class=\"text-center\">Response</h4>");
			out.print("<p class=\"text-center\"> Task ID: " + resp.getTaskId() + "</p>");
			
			for(String def : resp.getDefintions()) {
				out.print("<p> - " + def + "</p>");
			}
			
			out.print("</div>");
			out.print("<div class=\"text-center\"><a class=\"btn btn-primary\" href=\"index.jsp\">Make another query</a></div>");
		}

		// Closing tags
		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
		
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);"); //Refresh every 10 seconds
		out.print("</script>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
