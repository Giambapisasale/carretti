package valueobject;

import carretti.Carrello;
import carretti.Session;

public class Response {

	String result = null;
	String sessionCode = null;
	Boolean esito;
	
	Session sessionData;
	

	public Response() {
		this.esito = false;
	}
	
	
	
	
	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}
	 

	
	
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	

	public Session getSessionData() {
		return sessionData;
	}



	public void setSessionData(Session sessionData) {
		this.sessionData = sessionData;
	}



	



	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}

	

}
