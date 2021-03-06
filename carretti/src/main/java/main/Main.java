package main;

//import javax.security.auth.callback.CallbackHandler;
//import javax.security.auth.login.LoginContext;
//import javax.security.auth.login.LoginException;

//import carretti.Carrello;
//import carretti.Client;
import services.SessionServiceImpl;
import valueobject.Request;
//import utils.LoginCallbackHandler;
import valueobject.Response;
import carretti.Server;
import carretti.Shop;

public class Main {

	public static void printSessionInfoFromResponse(Response ret) {
		System.out
				.println("----------------------------------------------------------------------");
		/* Esito della login */
		System.out.println("*[Main]* esito login :" + ret.getEsito());
		/* Session popolata dall'aspetto */
		System.out.println("*[Main]*:ret.getSessionData(): "
				+ ret.getSessionCode());

		/* Recupero la sessione memorizzata nell'aspetto */
		System.out.println("*[Main]* Sessione Utente:"
				+ SessionServiceImpl.getInstance()
						.findSessionByKey(ret.getSessionCode()).getCodice());

	}

	public static void printProductsInCart(Server server) {
		System.out
				.println("----------------------------------------------------------------------");
		System.out.println("Carrello Utente");
		try {
			System.out.println("PRODOTTI:"
					+ server.getSession().getCarrello().getListaProdotti());

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Carrello Vuoto");
		}

	}

	public static void main(String[] args) {

		final String username = "user1";
		final String password = "user1";

		/* Start Shop */
		Shop.InitShop();

		/* Effettuo login */
		Server server = new Server();
		Response ret = server.login(username, password);
		Request request = new Request();
		request.setSessionCode(server.getSession().getCodice());

		printSessionInfoFromResponse(ret);
		printProductsInCart(server);

		server.addProdotto("A04", 1, request);
		server.logout();

		/*
		 * System.setProperty("java.security.auth.login.config", "jaas.config");
		 * 
		 * boolean loginStatus = true;
		 * 
		 * //CallbackHandler handler = new
		 * LoginCallbackHandler(username,password);
		 * 
		 * LoginContext ctx = null; try { ctx = new
		 * LoginContext("CarrettiLogin",new LoginCallbackHandler(username,
		 * password)); } catch(LoginException le) {
		 * System.err.println("LoginContext cannot be created. "+
		 * le.getMessage()); System.exit(-1); } catch(SecurityException se) {
		 * System.err.println("LoginContext cannot be created. "+
		 * se.getMessage()); } try { ctx.login(); } catch(LoginException le) {
		 * System.out.println("Authentication failed. " + le.getMessage());
		 * System.exit(-1); } System.out.println("Authentication succeeded.");
		 * System.exit(-1); } /*
		 * 
		 * try {
		 * 
		 * //LoginContext loginContext = new LoginContext("CarrettiLogin" ,
		 * handler); LoginContext loginContext = new
		 * LoginContext("CarrettiLogin",new LoginCallbackHandler(username,
		 * password)); loginContext.login();
		 * 
		 * } catch (LoginException e) { System.out.println("loginStatus");
		 * loginStatus = false; e.printStackTrace();
		 * 
		 * }
		 */
	}
}
