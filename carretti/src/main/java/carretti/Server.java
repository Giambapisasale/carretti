package carretti;

public class Server {

	final String users []  = {"user1", "user2"};
	
	/**
	 * Login utente
	 * simula l'autenticazione verificando presenza utenti 
	 * @param user
	 * @param password
	 * @return user se login è corretto
	 */
	public String login(String username, String password) {
		for(String user: users) {
			if(user.equals(username)) return user;
		}
		return null;
	}
	
	/**
	 * empty utility method
	 */
	public void logout() {
		return;
	}
	
	
	
}
