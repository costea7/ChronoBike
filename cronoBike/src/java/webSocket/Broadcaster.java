package webSocket;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webSocket.SimpleWSHandler;

/**
 * Servlet implementation class Broadcaster
 */
@WebServlet("/broadcast")
public class Broadcaster extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleWSHandler.broadcastMessage("Hello World");
	}
}