package carretti;

import java.util.HashMap;
import java.util.List;

import valueobject.Prodotto;

public class Carrello {

	
	private Integer id;
	
	
	
	// Lista di codici di prodotto legata alla quantit�
	// presente nel carrello in un dato momento
	private HashMap<String, Integer> prodotti = null;

	/**
	 * Aggiunge un Prodotto al carrello dato il codice Incrementa la quantit� se
	 * il prodotto � gi� presente Inizializzazione lazy del carrello
	 * 
	 * @param codice
	 *            codice prodotto
	 * @param quantita
	 *            quantita da aggiungere al carrello
	 */
	public void addByCodice(String codice, int quantita) {
		// aggiunge un prodotto al carrello per codice

		// TODO spostare l'inizializzazione laxy in un aspetto dedicato
		if (prodotti == null) {
			prodotti = new HashMap<String, Integer>();
		}

		Integer quantitaAttuale = 0;
		if (prodotti.containsKey(codice)) {
			quantitaAttuale = prodotti.get(codice);
		}
		quantitaAttuale += quantita;

		prodotti.put(codice, quantitaAttuale);

		return;

	}

	/**
	 * Rimuove un prodotto dal carrello dato il codice, decrementa la quantit�
	 * se il prodotto � gi� presente
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
			
			Integer quantitaAttuale = prodotti.get(codice);
			quantitaAttuale -= Math.abs(quantita);
			if (quantitaAttuale <= 0) {
				prodotti.remove(codice);
			} else {
				prodotti.put(codice, quantitaAttuale);
			}
		} else {
			throw new Exception("non ci sono prodotti con questo codice");
		}
		return;
	}
	
	public HashMap<String, Prodotto> getListaProdotti () {
		HashMap<String, Prodotto> carrello = new HashMap<String, Prodotto>();
		for(String codice : prodotti.keySet()) {
			
			// recupero una istanza dello shop
			// TODO passare a singleton, simula una chiamata al database
			Shop shop = new Shop();
			// recupero un prodotto a partire dal codice
			Prodotto p = shop.getProdottoByCodice(codice);
			
			if(p != null) {
				// recupero la quantit� attualmente presente nel carrello
				// la associo ad un prodotto esistente
				carrello.put(p.getCodice(), p);
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
