package org.jboss.demo.cluster.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ClusteredServlet")
public class ClusteredServlet extends HttpServlet {

	private static final long serialVersionUID = -6968732566653569245L;
	
	private static final DateFormat TIME_FORMAT = new SimpleDateFormat( "EEEE 'at' h:mm:ss aaa" );
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "ClusteredServlet" );
		HttpSession session = request.getSession( false );
		if (session == null) {
			session = request.getSession(true);
			session.setAttribute("initialization", new Date());
			session.setAttribute("initial_server", System.getProperty("jboss.server.name"));
		}
		if (request.getParameter("save") != null) {
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			if (key.length() > 0) {
				if (value.length() == 0) {
					session.removeAttribute(key);
				} else {
					session.setAttribute(key, value);
				}
			}
		}
		PrintWriter writer = response.getWriter();
		writer.println( "<html>" );
		writer.println( "<head>" );
		writer.println( "</head>" );
		writer.println( "<body>" );
		StringBuilder welcomeMessage = new StringBuilder();
		welcomeMessage.append( "HTTP Request received " );
		welcomeMessage.append( TIME_FORMAT.format( new Date() ) );
		welcomeMessage.append( " on server <b>" );
		welcomeMessage.append( System.getProperty( "jboss.server.name" ) );
		welcomeMessage.append( "</b>" );
		writer.println( welcomeMessage.toString() );
		writer.println( "<p/>" );

		writer.println( "<form action='' method='post'>" );
		writer.println( "Store value in HTTP session:<br/>" );
		writer.println( "Key: <input type=\"text\" name=\"key\"><br/>" );
		writer.println( "Value: <input type=\"text\" name=\"value\"><br/>" );
		writer.println( "<input type=\"submit\" name=\"save\" value=\"Save\">" );
		writer.println( "</form>" );
		writer.println( "<table border='1'>" );
		Enumeration<String> attrNames = session.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			writer.println("<tr>");
			String name = (String) attrNames.nextElement();
			Object value = session.getAttribute(name);
			if (value instanceof Date) {
				Date date = (Date) value;
				value = TIME_FORMAT.format(date);
			}
			writer.print("<td>");
			writer.print(name);
			writer.println("</td>");
			writer.print("<td>");
			writer.print(value);
			writer.println("</td>");
			writer.println("</tr>");
		}
		writer.println("</table>");
		writer.println("</body>");
		writer.println("</html>");
		response.setContentType("text/html;charset=utf-8");
		writer.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet( request, response );
	}

}
