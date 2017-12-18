package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PollingServlet
 */
public class PollingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PollingServlet() {
		super();
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
		out.print("<p id=\"waiting\" class=\"text-center\">Waiting for response...Polling </p>");
		
		out.print("<form name=\"frmRequestDetails\">");
		//out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
		out.print("</form>");

		// Closing tags
		out.print("</div>");
		out.print("</body>");
		out.print("</html>");
		
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);"); //Refresh every 5 seconds
		out.print("</script>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
