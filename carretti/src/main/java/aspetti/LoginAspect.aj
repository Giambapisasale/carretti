package aspetti;

import valueobject.Response;
import utils.GenerateId;
import carretti.Server;
import carretti.Session;
import services.ClientServiceImpl;

public aspect LoginAspect {

		pointcut trapUserLogin(String username, String password) : 
			args(username,password) && 
			call(public Response login(String,String)); 


		before(String username, String password) : trapUserLogin(username , password ) {
			
			// System.out.println("* Before UserLogin paramters :"+username+' '+password);
		}
		after(String username, String password) : trapUserLogin(username, password) {
			//System.out.println("* After UserLogin parameters:"+username+' '+password);
		}
		
		/* Intercetto il valore di ritorno della funzione login()*/
		after(String username, String password) returning(Response r): trapUserLogin(username, password) {
				System.out.println("* after con valore di ritorno ="+r.getEsito());
				//System.out.println("* after con parametri ="+username+" " +password);

				if (r.getEsito()) {
					System.out.println("* Login ok ");
					
					/*
					 * controllo se l'utente era già presente nel sistema
					 */
					String UserHasSession = isReturnedUser(username);
				    
					String userSessionid = (UserHasSession != null && !UserHasSession.isEmpty()) ? 
							UserHasSession : initSessionId();
					
					setSession(userSessionid);
					
					
					
				} else System.out.println("* Login failed");
		 }
		
		private String isReturnedUser(String login) {
			ClientServiceImpl cl = new ClientServiceImpl();
			String ret = cl.findUserByEmail(login);
			if (ret != null && !ret.isEmpty()) {
					return ret;
			}
			return null;
		}
		
		private String initSessionId() {
			GenerateId myId = new GenerateId();
			String SessionID = myId.generateRandomId();
			return SessionID;
		}
		private void setSession(String sessionID) {
			Session session = new Session();
			session.setCodice(sessionID);
			Server server = new Server();
			server.setSession(session);
			System.out.println("**Save User Session**"+server.getSession().getCodice());
		}
		


}
