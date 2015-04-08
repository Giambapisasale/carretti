package carretti; 
 
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import valueobject.Prodotto;
import valueobject.ProdottoCarrello;
import valueobject.Request;
import valueobject.Response;

public class TestServer {
	private Server server;
	
	private final static String USR = "user1";
	private final static String PWD = "pwd";
	
	private static int Q1, Q2, Q3, Q4, Q5;
	static {
		Q1 = 10;
		Q2 = 20;
		Q3 = 15;
		Q4 = 17;
		Q5 = 10;
	}
	
	private static Prodotto P1, P2, P3, P4, P5;
	static {
		P1 = new Prodotto("FAKE", "NAME", 10F);
		P2 = new Prodotto("A01", "Bottiglietta Ferrarelle", .5F);
		P3 = new Prodotto("A02", "Computer asus", 1000F);
		P4 = new Prodotto("A03", "Cuffie cellulare", 20.5F);
		P5 = new Prodotto("A04", "Mouse TRUST", 12F);
	}
	
	private static ProdottoCarrello PC1, PC2, PC3, PC4, PC5;
	static {
		PC1 = new ProdottoCarrello(P1, Q1);
		PC2 = new ProdottoCarrello(P2, Q2);
		PC3 = new ProdottoCarrello(P3, Q3);
		PC4 = new ProdottoCarrello(P4, Q4);
		PC5 = new ProdottoCarrello(P5, Q5);
	}
	
	@Before
	public void initServer() {
		server = new Server();
	}
	
	@After
	public void destroyServer() {
		server = null;
	}
	
	/**
	 * @param flag if <code>true</code> the request is correct, else otherwise
	 * @return {@link Request}
	 */
	private final Request createRequest(final boolean flag) {
		Response response = server.login(USR, PWD);
		Request request = new Request();
		if(flag)
			request.setSessionCode(response.getSessionCode());
		else
			request.setSessionCode("FAKE SESSION");
		return request;
	}
	
	private final HashMap<String, ProdottoCarrello> createListProdottoCarrello(
			final ProdottoCarrello ...products) {
		HashMap<String, ProdottoCarrello> prods = new HashMap<String, ProdottoCarrello>();
		for(final ProdottoCarrello p : products)
			prods.put(p.getProduct().getCodice(), p);
		return prods;
	}
	/**
	 * aggiunge dei prodotti che esistono nello shop
	 */
	@Test
	public void testAddProducts() {
		Request request = createRequest(true);
		server.addProdotto(P2.getCodice(), Q2, request);
		server.addProdotto(P3.getCodice(), Q3, request);
		server.addProdotto(P4.getCodice(), Q4, request);
		HashMap<String, ProdottoCarrello> actual = server.getListaProdottiCarrello(request);
		HashMap<String, ProdottoCarrello> expected = createListProdottoCarrello(
				PC2, PC3, PC4);
		assertEquals("The list is not equals", expected, actual);
	}
}
