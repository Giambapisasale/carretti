package carretti;

import java.util.HashMap;
import java.util.List;

import valueobject.Prodotto;
import valueobject.Request;
import valueobject.Response;

public class Server {

	final String users[] = { "user1", "user2" };
	
	Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Login utente simula l'autenticazione verificando presenza utenti
	 * 
	 * @param user
	 * @param password
	 * @return Response popolata con i valori di ritorno
	 */
	public Response login(String username, String password) {
		Response response = new Response();

		for (String user : users) {
			if (user.equals(username)) {
				response.setResult(user);
				response.setEsito(true);
			}
		}
		return response;
	}

	/**
	 * empty utility method
	 */
	public void logout() {
		return;
	}
	
	/**
	 * restituisce la lista di tutti i prodotti disponibili nello shop
	 * @return
	 */
	public List<Prodotto> getListaProdotti() {
		return new Shop().getProdotti();
	}

	/**
	 * Recupera la lista di prodotti attualmente presenti nel carrello
	 * @param request
	 * @return
	 */
	/*
	public HashMap<String, Prodotto> getListaProdottiCarrello(Request request) {
		return getSession().getCarrello().getListaProdotti();
	}
	*/
	
	/**
	 * aggiunge una quantita di prodotto al carrello
	 * @param codice
	 * @param quantita
	 */
	public void addProdotto(String codice, int quantita, Request request) {
		getSession().getCarrello().addByCodice(codice, quantita);
		return;
	}
	
	/**
	 * rimuove una quantita di prodotto dal carrello
	 * @param codice
	 * @param quantita
	 * @throws Exception 
	 */
	
	public void removeProdotto(String codice, int quantita, Request request) throws Exception {
		getSession().getCarrello().removeByCodice(codice, quantita);
		return;
	}
	
}
