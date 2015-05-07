package aspetti;

import java.util.Calendar;

import services.CartServiceImpl;
import services.ClientServiceImpl;
import services.SessionServiceImpl;
import utils.GenerateId;
import valueobject.Request;
import valueobject.Response;
import carretti.Carrello;
import carretti.Server;
import carretti.Session;
import carretti.Shop;

public aspect LoginAspect {

	String fake_session = "FAKE_SESSION";
	final long TRENTA_MINUTI_LONG = 30L * 1000L * 60L;

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

	pointcut trace_server(Server srv, String s, int q, Request r): 
			args(s,q,r) && target(srv) &&
			( 		call(public * getListaProdotti(Request)) ||
					call(public void addProdotto(String, int, Request)) ||
					call(public void removeProdotto(String,int,Request)) 
			);

	
	pointcut trap_ShopInit(Shop shop) :
			target(shop) &&
				execution(Shop.new());

	pointcut logout(Server srv):  target (srv)  && call(public void logout());

	void around(Server srv) : logout(srv) {
		System.out.println("*[Aspect]*: Aroud on logout ");

		SessionServiceImpl.getInstance().destroySessionByKey(
				srv.getSession().getCodice());
		srv.setSession(null);
		proceed(srv);
	}

	after(Shop shop) : trap_ShopInit(shop) {
		shop.getProdotti();
		System.out.println("*[Aspect]*: Inserisco prodotti nel negozio");
	}

	

	/*
	 * Verifica che la sessione sia stata creata
	 */
	before(Server srv, String s, int q, Request req) : trace_server(srv, s,q,req) {
		if (req.getSessionCode().equals(fake_session)) {
			System.out.println("*[Aspect]*:Session Error on "
					+ thisJoinPoint.getSignature());
		}
		/* check session timestamp */
		if (checkSessionTimestamp(srv.getSession())) {
			System.out.println("*[Aspect]*: Session e' ancora valida");
		} else {
			System.out
					.println("*[Aspect]*: Session expired. Redirect to logout");
			srv.logout();
		}
	}

	/* Intercetto il valore di ritorno della funzione login() */
	after(Server server, String username, String password) returning(Response r): trapLoginExecution(server,username,password) {
		System.out.println("*[Aspect]* after con valore di ritorno ="
				+ r.getEsito());

		/* login riuscita */
		if (r.getEsito()) {
			System.out.println("*[Aspect]* Login ok ");

			/*
			 * controllo se l'utente era gia' presente nel sistema
			 */
			Session session = findSession(username);
			// TODO dopo ogni accesso bisogna resettare il timer di scadenza
			
			/*
			 * se non trovo la sessione per l'utente corrente, ne creo una nuova
			 */
			if(session == null ) {
				String userSessionid = initSessionId();
				session = setSession(userSessionid, username);				
			} 
			
			/* salvo il codice della Session in Response */
			r.setSessionCode(session.getCodice());
			/* Salvo l'obj Session simulando la persistenza */
			persistSession(session);
			/* salvo la session nell'instanza del server */
			server.setSession(session);
		} else
			System.out.println("*[Aspect]* Login failed");
	}

	/*
	 * @param user login Ricerca la chiave login
	 * 
	 */
	private Session findSession(String login) {
		SessionServiceImpl sessionService = SessionServiceImpl.getInstance();
		Session ret = sessionService.findSessionByLogin(login);
		return ret;
	}

	/*
	 * genera un ID di session
	 */
	private String initSessionId() {
		GenerateId myId = new GenerateId();
		String sessionID = myId.generateRandomId();
		return sessionID;
	}

	/*
	 * @param sessionID inizializza una nuova istanza di Session e salva il
	 * codice sessionID e e il carrello utente N.B: da integrare con nuove
	 * funzioni di gestione del carrello
	 */
	private Session setSession(String sessionID, String username) {
		Session session = new Session();

		long creationDate = Calendar.getInstance().getTime().getTime();
		session.setCodice(sessionID);
		session.setCreazione(creationDate);
		session.setCarrello(getUserCart(sessionID));
		session.setUtente(username);
		
		return session;
	}

	/*
	 * @param Session sessionID
	 * 
	 * @return istanza di Carrello
	 * 
	 * controllo se esite un carrello salvato in sessione.
	 */
	private Carrello getUserCart(String sessionID) {

		CartServiceImpl cartService = new CartServiceImpl();
		try {
			Carrello myCart = cartService.getCartBySessionId(sessionID);
			System.out.println("*[Aspect]* Carrello.id :" + myCart.getId());
			return myCart;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * @param Session session simula la persistenza dei dati di session,
	 * memorizzando la chiave in una classe Singleton
	 */
	private void persistSession(Session session) {
		SessionServiceImpl.getInstance().saveSession(session);
		System.out.println("*[Aspect]* SESSIONE SALVATA:"
				+ SessionServiceImpl.getInstance()
						.findSessionByKey(session.getCodice()).getCodice());

	}

	/**
	 * simula il controllo del timestamp associato alla Sessione
	 * utente.
	 * @param Session session
	 * 
	 * @return Boolean true se la sessione è ancora valida
	 */
	private Boolean checkSessionTimestamp(Session session) {
		Long sessionCreated = session.getCreazione();
		Long now = Calendar.getInstance().getTime().getTime();
		return now - sessionCreated < TRENTA_MINUTI_LONG;

	}
}
