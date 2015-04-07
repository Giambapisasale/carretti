package valueobject;

import carretti.Carrello;
import carretti.Session;

public class Response {
	String result = null;
	String sessionCode = null;
	Boolean esito;
	
	

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

	public Boolean getEsito() {
		return esito;
	}

	public void setEsito(Boolean esito) {
		this.esito = esito;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [result=" + result + ", sessionCode=" + sessionCode
				+ ", esito=" + esito + "]";
	}

}
