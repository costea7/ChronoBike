package webSocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/simple")
public class SimpleWSHandler {
	Logger logger = Logger.getLogger(getClass().getName());
	private static final Set<Session> sessions = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void open(Session session) {
		sessions.add(session);
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		try {
			logger.info(String.format("Got message: %s", msg));

			session.getBasicRemote().sendText(msg.toUpperCase());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error", e);
		}
	}

	@OnClose
	public void close(Session session, CloseReason reason) {
		sessions.remove(session);
	}

	public static void broadcastMessage(String message) {
		for (Session s : sessions) {
			if (s.isOpen()) {
				try {
					s.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}