package aspetti;

import valueobject.Response;
import utils.GenerateId;
import carretti.Carrello;
import carretti.Server;
import carretti.Session;
import services.ClientServiceImpl;

public aspect LoginAspect {

		pointcut trapUserLogin(String username, String password) : 
			args(username,password) && 
			call(public Response login(String,String)); 


		before(String username, String password) : trapUserLogin(username , password ) {
			
		}
		after(String username, String password) : trapUserLogin(username, password) {
			//System.out.println("* After UserLogin parameters:"+username+' '+password);
		}
		
		/* Intercetto il valore di ritorno della funzione login()*/
		after(String username, String password) returning(Response r): trapUserLogin(username, password) {
				System.out.println("* after con valore di ritorno ="+r.getEsito());
	
				if (r.getEsito()) {
					System.out.println("* Login ok ");
					
					/*
					 * controllo se l'utente era già presente nel sistema
					 */
					String UserHasSession = isReturnedUser(username);

					String userSessionid = (UserHasSession != null && !UserHasSession.isEmpty()) ? 
							UserHasSession : initSessionId();
					System.out.println("*****userSessionid ="+userSessionid);
					
					Session session = setSession(userSessionid);
					System.out.println("*****session.getCodice() ="+session.getCodice());

					/* salvo il codice della Session in Response */
					r.setSessionCode(session.getCodice());
					
					System.out.println("****Cart:"+session.getCarrello().getId());
					
					
					
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
		private Session setSession(String sessionID) {
			Session session = new Session();
			session.setCodice(sessionID);
			session.setCarrello(getUserCart());
			return session;
			
		}
		private Carrello getUserCart() {
			Carrello cart = new Carrello();
			cart.setId(101);
			return cart;
		}
		


}
