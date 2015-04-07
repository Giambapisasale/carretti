package services;

import java.util.Hashtable;

import carretti.Session;

public class SessionServiceImpl implements SessionService {

	Hashtable<String, Session> sessionData = new Hashtable<String,Session>();

	
	private static SessionServiceImpl instance ;
	
	private SessionServiceImpl() {}
	public static SessionServiceImpl getInstance() 
	{
		if (instance == null ) {
			instance = new SessionServiceImpl();
		}
		return instance;
	}
	
	@Override
	public void saveSession(Session session) {
		sessionData.put(session.getCodice(), session);

	}

	@Override
	public Session findSessionByKey(String key) {
		return sessionData.get(key);

	}

}
