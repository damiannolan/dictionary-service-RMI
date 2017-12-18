package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DictionaryServlet
 * 
 * 1. Handle incoming request from web client 2. Take request and add to inQueue
 * 3. Return requestId to web client 4. Start RMI Client thread - pull req from
 * in queue 5. Make RMI Call 6.
 */
public class DictionaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestQueue reqQueue;
	private static long taskNumber = 0;

	public DictionaryServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		// Instantiate the Request Queue upon initialization of the Servlet
		try {
			this.reqQueue = new RequestQueue();
		} catch (IOException e) {
			System.out.println("Error initializing queue with RabbitMQ");
			e.printStackTrace();
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		out.print("<p id=\"waiting\" class=\"text-center\">Waiting for response... </p>");

		out.print("<form name=\"frmRequestDetails\" action=\"PollingServlet\">");
		// out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title
		// + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");

		// JavaScript to periodically poll the server for updates (this is ideal
		// for an asynchronous operation)
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); // Refresh
		out.print("</script>");

		// Closing tags
		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("queryText"));
		System.out.println(taskNumber);
		
		// Create a new Request Object - passing it a taskId and query string
		Request req = new Request(taskNumber, request.getParameter("queryText"));
		// Queue the request with RabbitMQ
		this.reqQueue.queueRequest(req);
		// Create a new RMIClient worker and start the thread
		Thread worker = new Thread(new RMIClient());
		worker.start();
		
		// Increment the taskNumber
		taskNumber++;

		doGet(request, response);
	}

}
