package services;

import java.util.Hashtable;
import java.util.Random;
import carretti.Carrello;

public class CartServiceImpl implements CartService {

	Hashtable<String, Carrello> cartData = new Hashtable<String, Carrello>();

	/*
	 * @param sessionId : identificatore della sessione utente Simulo operazione
	 * DAO per recuperare il carrello dell'utente
	 */

	public CartServiceImpl() {
		try {
			populateDummyData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Carrello getCartBySessionId(String sessionId) throws Exception {
		/* Simulo dati provenienti da DAO */
		Carrello stored_cart = cartData.get(sessionId);
		if (stored_cart != null) {
			System.out
					.println("*[CartServiceImpl]* Trovato Carrello Salvato con Id  "
							+ stored_cart.getId());
			return stored_cart;
		} else {
			Random randomGenerator = new Random();
			Carrello newCart = new Carrello();
			newCart.setId(randomGenerator.nextInt(100));
			System.out
					.println("*[CartServiceImpl]* Generato nuovo Carrello con Id  "
							+ newCart.getId());
			return newCart;
		}
	}

	public void setCartBySessionId(Carrello cart, String sessionId) {

		cartData.put(sessionId, cart);

	}

	public void populateDummyData() throws Exception {
		Carrello cart = new Carrello();
		cart.setId(1000);
		cart.addByCodice("A01", 10);
		cart.addByCodice("A02", 12);
		cart.addByCodice("A03", 15);
		setCartBySessionId(cart, "f543c2ccfa550171f5816c9fdc310165c7e1cc10");

	}

}
