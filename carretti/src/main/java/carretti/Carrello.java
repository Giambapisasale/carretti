package carretti;

import java.util.HashMap;

import valueobject.Prodotto;
import valueobject.ProdottoCarrello;

public class Carrello {

	
	private Integer id;
	
	
	
	private HashMap<String, ProdottoCarrello> products = null;
	
	/**
	 * Lista di codici di prodotto legata alla quantità 
	 * presente nel carrello in un dato momento
	 */
	private HashMap<String, ProdottoCarrello> prodotti = null;

	/**
	 * Aggiunge un Prodotto al carrello dato il codice Incrementa la quantità se
	 * il prodotto è già presente Inizializzazione lazy del carrello
	 * 
	 * @param codice
	 *            codice prodotto
	 * @param quantita
	 *            quantita da aggiungere al carrello
	 * @throws Exception 
	 */
	public void addByCodice(String codice, int quantita) throws Exception {
		// aggiunge un prodotto al carrello per codice

		// TODO spostare l'inizializzazione laxy in un aspetto dedicato
		if (prodotti == null) {
			prodotti = new HashMap<String, ProdottoCarrello>();
		}
		// recupero una istanza dello shop
		// TODO passare a singleton, simula una chiamata al database
		Shop shop = new Shop();
		// recupero un prodotto a partire dal codice
		Prodotto p = shop.getProdottoByCodice(codice);
		
		if(p == null)
			throw new Exception("nessun prodotto con il codice " + codice + " è stato"
					+ " trovato");
		Integer quantitaAttuale = 0;
		if (prodotti.containsKey(codice)) {
			quantitaAttuale = prodotti.get(codice).getQuantity();
		}
		quantitaAttuale += quantita;
		prodotti.put(codice, new ProdottoCarrello(p, quantitaAttuale));
//		prodotti.put(codice, quantitaAttuale);

		return;

	}

	/**
	 * Rimuove un prodotto dal carrello dato il codice, decrementa la quantitï¿½
	 * se il prodotto ï¿½ giï¿½ presente
	 * 
	 * 
	 * @param codice
	 *            codice prodotto
	 * @param quantita
	 *            quantita da rimuovere dal carrello
	 * @throws Exception 
	 */
	public void removeByCodice(String codice, int quantita) throws Exception {
		if (prodotti != null && prodotti.size() > 0
				&& prodotti.containsKey(codice)) {
			
			ProdottoCarrello p = prodotti.get(codice);
			p.setQuantity(p.getQuantity() - Math.abs(quantita));
			if (p.getQuantity() <= 0) {
				prodotti.remove(codice);
			} else {
				prodotti.put(codice, p);
			}
		} else {
			throw new Exception("non ci sono prodotti con questo codice");
		}
		return;
	}
	
	public HashMap<String, ProdottoCarrello> getListaProdotti () {
		HashMap<String, ProdottoCarrello> carrello = new HashMap<String, ProdottoCarrello>();
		for(String codice : prodotti.keySet()) {
			  
			// recupero una istanza dello shop
			// TODO passare a singleton, simula una chiamata al database
			Shop shop = new Shop();
			// recupero un prodotto a partire dal codice
			Prodotto p = shop.getProdottoByCodice(codice);
			
			if(p != null) {
				// recupero la quantità attualmente presente nel carrello
				// la associo ad un prodotto esistente
				carrello.put(p.getCodice(), prodotti.get(codice));
			}
		}
		return carrello;
	}

	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
}
