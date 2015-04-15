package aspetti; 

import java.util.HashMap;
import java.util.List;
import java.util.Random;




import valueobject.Prodotto;
import valueobject.ProdottoCarrello;
import valueobject.Response;
import valueobject.Request;
import utils.GenerateId;
import carretti.Carrello;
import carretti.Session;
import carretti.Server;
import carretti.Shop;

import services.CartServiceImpl;
import services.ClientServiceImpl;
import services.SessionServiceImpl;


public aspect LoginAspect {
		
		String fake_session = "FAKE_SESSION";
	
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
		
		/* pointcut Server */
		 
		
		
		pointcut trace_server(String s, int q, Request r): 
			args(s,q,r) && 
			( 		call(public * getListaProdotti(Request)) ||
					call(public void addProdotto(String, int, Request)) ||
					call(public void removeProdotto(String,int,Request)) 
			);
		
		pointcut trap_addProdotto(Server srv,  String s, int q, Request r):
			args(s,q,r) && target(srv)  &&
				call(public void addProdotto(String, int, Request));
		
		pointcut trap_ShopInit(Shop shop) :
			target(shop) &&
				execution(Shop.new());
		
		/*
		around(String s, int q, Request r) : trap_addProdotto(s,q,r) {
			System.out.println("*[Aspect]*: addProdotto ");
			
		}
		*/
		after(Shop shop) : trap_ShopInit(shop) {
			shop.getProdotti();
			System.out.println("*[Aspect]*: Inserisco prodotti nel negozio");
		}
		after(Server srv,  String s, int q, Request r) : trap_addProdotto(srv,  s,q,r) {
			System.out.println("**CARRELLO**: "+srv.getSession().getCarrello().getId());
		}
				
		/*
		 * Verifica che la sessione sia stata creata
		 */
		before(String s, int q, Request req) : trace_server(s,q,req) {
			if ( req.getSessionCode().equals(fake_session)) {
				System.out.println("*[Aspect]*:Session Error on " + 
						thisJoinPoint.getSignature());
			}
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
					//System.out.println("*[Aspect]*session.getCodice() ="+session.getCodice());

					/* salvo il codice della Session in Response */
					r.setSessionCode(session.getCodice());
				//	System.out.println("*[Aspect]*Cart:"+session.getCarrello().getId());
					
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
			session.setCarrello(getUserCart(sessionID));
			return session;
		}
		
		
		/*
		 * @param Session sessionID
		 * @return istanza di Carrello
		 * 
		 * controllo se esite un carrello salvato in sessione.
		 */
		 private Carrello getUserCart(String sessionID) {

			CartServiceImpl cartService = new CartServiceImpl();
			try {
				Carrello myCart = cartService.getCartBySessionId(sessionID);
				System.out.println("*[Aspect]* Carrello.id :"+ myCart.getId() );
				return myCart;

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
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
