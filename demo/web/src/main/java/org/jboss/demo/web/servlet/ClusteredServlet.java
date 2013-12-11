package org.jboss.demo.web.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ClusteredServlet")
public class ClusteredServlet extends HttpServlet {
	
    private static final long serialVersionUID = -946741803216943778L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession(false);
    	if(null != session){
    		System.out.println("Current Request Session ID: " + session.getId());
    		System.out.println("Current Request Session Items: ");
    		Enumeration<String> attrNames = session.getAttributeNames();
    		while (attrNames.hasMoreElements()) {
    			String name = (String) attrNames.nextElement();
    			Object value = session.getAttribute(name);
    			System.out.println("  " + name + " -> " + value);
    		}
    	} else {
    		System.out.println("Current Request Session is null");
    	}
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
