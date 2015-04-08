package aspetti; 

import java.util.HashMap;

import valueobject.ProdottoCarrello;
import valueobject.Response;
import valueobject.Request;

import utils.GenerateId;
import carretti.Carrello;
import carretti.Session;
import carretti.Server;

import services.ClientServiceImpl;
import services.SessionServiceImpl;


public aspect LoginAspect {

		pointcut trapUserLogin(String username, String password) : 
			args(username,password) && 
			call(public Response login(String,String)); 

		
		pointcut trapLoginExecution(Server server, String username, String password):
			execution(public Response Server.login(String, String)) &&
			target(server) &&
			args(username,password);
		
		
		pointcut trapCartInit(Carrello cart) : 
			execution(Carrello.new()) &&
			target(cart);
		
		/*
		 * 	public HashMap<String, ProdottoCarrello> getListaProdottiCarrello(Request request) {

		 */
		pointcut trap_getListaProdottiCarrello(Request request):
			call(public HashMap<String, ProdottoCarrello> getListaProdottiCarrello(Request) ) 
			&& target(request);
		
		
		before(Request request) : trap_getListaProdottiCarrello(request) {
			System.out.println("BEFORE LISTA PRODOTTI"+thisJoinPoint.getTarget());

		}
		
		
		
		
		
		before(String username, String password) : trapUserLogin(username , password ) {
			
		}
		after(String username, String password) : trapUserLogin(username, password) {
			//System.out.println("* After UserLogin parameters:"+username+' '+password);
		}
		
		/* Intercetto il valore di ritorno della funzione login()*/
		after(Server server, String username, String password) returning(Response r): trapLoginExecution(server,username,password) {
				System.out.println("*[Aspect]* after con valore di ritorno ="+r.getEsito());

				/* login riuscita */
				if (r.getEsito()) {
					System.out.println("*[Aspect]* Login ok ");
					
					/*
					 * controllo se l'utente era già presente nel sistema
					 */
					String UserHasSession = isReturnedUser(username);

					/*
					 * recupero o assegno sessione
					 */
					String userSessionid = (UserHasSession != null && !UserHasSession.isEmpty()) ? 
							UserHasSession : initSessionId();
					//System.out.println("*[Aspect]*userSessionid ="+userSessionid);
					Session session = setSession(userSessionid);
					System.out.println("*[Aspect]*session.getCodice() ="+session.getCodice());

					/* salvo il codice della Session in Response */
					r.setSessionCode(session.getCodice());
					System.out.println("*[Aspect]*Cart:"+session.getCarrello().getId());
					
					/* Salvo l'obj Session simulando la persistenza */
					persistSession(session);
					
					/* salvo la session nell'instanza del server */
					server.setSession(session);
					
			
				} else System.out.println("*[Aspect]* Login failed");
		 }
		
		
		/*
		 * @param user login 
		 * Ricerca la chiave login 
		 */
		private String isReturnedUser(String login) {
			ClientServiceImpl cl = new ClientServiceImpl();
			String ret = cl.findUserByEmail(login);
			if (ret != null && !ret.isEmpty()) {
					return ret;
			}
			return null;
		}

		
		/*
		 * genera un ID di session
		 */
		private String initSessionId() {
			GenerateId myId = new GenerateId();
			String SessionID = myId.generateRandomId();
			return SessionID;
		}
		/*
		 * @param sessionID 
		 * inizializza una nuova istanza di Session e salva il codice sessionID e
		 * e il carrello utente
		 * N.B: da integrare con nuove funzioni di gestione del carrello
		 */
		private Session setSession(String sessionID) {
			Session session = new Session();
			session.setCodice(sessionID);
			session.setCarrello(getUserCart());
			return session;
		}
		
		/*
		 * Provvisoria: setta un id del carrello  
		 */
		
		private Carrello getUserCart() {
			Carrello cart = new Carrello();
			cart.setId(101);
			return cart;
		}
		
		
		/*
		 * @param Session session
		 * simula la persistenza dei dati di session, memorizzando la chiave in una classe Singleton
		 */
		private void persistSession(Session session) {
			SessionServiceImpl.getInstance().saveSession(session);
			System.out.println("*[Aspect]* SESSIONE SALVATA:"+SessionServiceImpl.getInstance().findSessionByKey(session.getCodice()).getCodice() );
			
		}
}
