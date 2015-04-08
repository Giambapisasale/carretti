package carretti;

import java.util.Date;

public class Session {

	String codice;
	
	String utente;
	
	Carrello carrello;
	
	Date creazione;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public Carrello getCarrello() {
		
		if(carrello == null) {
			carrello = new Carrello();
			System.err.println("sono qui dentro");
		}
		
		
		return carrello;
	}

	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}

	public Date getCreazione() {
		return creazione;
	}

	public void setCreazione(Date creazione) {
		this.creazione = creazione;
	}
	
	
	
}
