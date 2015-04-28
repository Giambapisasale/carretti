package carretti;

import java.util.ArrayList;
import java.util.List;

import valueobject.Prodotto;

public class Shop {

	private List<Prodotto> prodotti = null;
	private static Shop shop_instance;
	private Shop() {
		
	}
	public static Shop getInstance() {
		if( shop_instance == null) {
			shop_instance = new Shop();
		} 
		return shop_instance;
	}
	
	public static void InitShop() {
		getInstance();
	}
	
	
	/** 
	 * resituisce la lista di prodotti presenti nello shop
	 * inizializzazione lazy 
	 * @return Lista prodotti
	 */
	public List<Prodotto> getProdotti() {
		if (prodotti == null) {
			prodotti = popolaListaProdotti();
		}
		return prodotti;
	}

	private List<Prodotto> popolaListaProdotti() {
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		prodotti.add(new Prodotto("A01", "Bottiglietta Ferrarelle", .5F));
		prodotti.add(new Prodotto("A02", "Computer asus", 1000F));
		prodotti.add(new Prodotto("A03", "Cuffie cellulare", 20.5F));
		prodotti.add(new Prodotto("A04", "Mouse TRUST", 12F));
		prodotti.add(new Prodotto("A05", "Occhiali BOSS", 40F));
		prodotti.add(new Prodotto("A06", "Maglia grigia", 60F));
		return prodotti;
	}

	/**
	 * restituisce un prodotto dello Shop dato il codice
	 * 
	 * @param codice
	 * @return Prodotto oppure null se il codice non esiste
	 */
	public Prodotto getProdottoByCodice(String codice) {
		for (Prodotto prod : getProdotti()) {
			if(prod.getCodice().equalsIgnoreCase(codice)) {
				return prod;
			}
		}
		return null;
	}

}
