package services;

import carretti.Session;

public interface SessionService {

	public void saveSession(Session session);
	public Session findSessionByKey(String key);
	public void destroySessionByKey(String key);

}
