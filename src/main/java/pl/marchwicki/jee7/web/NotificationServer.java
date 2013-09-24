package pl.marchwicki.jee7.web;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@ServerEndpoint("/ping")
public class NotificationServer {
	Set<Session> sessions = new HashSet<>();
	
	@OnOpen
	public void onOpen(Session s) throws IOException {
		sessions.add(s);
		message("Opening new connection. Total connections: " + sessions.size(), s);
	}
	
	@OnClose
	public void onClose(Session s) throws IOException {
		sessions.remove(s);
		message("Closing connection. Total connections: " + sessions.size(), s);
	}
	
	@OnMessage
	public void message(String message, Session client) throws IOException {
		for (Session s: sessions) {
			if (s == client) continue; 
			try {
				s.getBasicRemote().sendText(message);
			} catch (Exception e) {
				onClose(s);
			}
		}		
	}
}
