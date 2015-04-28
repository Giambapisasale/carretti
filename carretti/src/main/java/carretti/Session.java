package carretti;


/**
 * Gestione della sessione. 
 * Quando un utente effettua il login viene creata una nuova sessione se non 
 * ve ne era una associata, altrimenti restituisce la sessione precedentemente
 * creata.
 * Alla sessione viene associato anche il {@link Carrello} relativo
 * all'utente.
 */
/**
 * @author salvatore
 *
 */
public class Session {

	String codice;
	
	String utente;
	
	Carrello carrello;
	
	Long creazione;

	/**
	 * @return string codice. Il codice della sessione 
	 */
	public String getCodice() {
		return codice;
	}

	
	/**
	 * @param codice. Imposta il codice della sessione
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}

	
	/**
	 * @return stringa che identifica l'utente a cui e' associata la sessione
	 */
	public String getUtente() {
		return utente;
	}

	/**
	 * @param utente imposta l'utente per la sessione che si sta creando
	 */
	public void setUtente(String utente) {
		this.utente = utente;
	}

	/**
	 * @return {@link Carrello}. Carrello associato alla sessione
	 */
	public Carrello getCarrello() {
		
		if(carrello == null) {
			carrello = new Carrello();
			System.err.println("sono qui dentro");
		}
		
		
		return carrello;
	}

	
	/**
	 * @param carrello imposta il carrello associato alla sessione
	 */
	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}

	/**
	 * 
	 * @return timestamp di quando e' stata creata la sessione
	 */
	public Long getCreazione() {
		return creazione;
	}
	
	/**
	 * 
	 * @param creazione imposta il timestamp quando si creea la sessione
	 */
	public void setCreazione(Long creazione) {
		this.creazione = creazione;
	}
}
