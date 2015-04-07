package services;

import java.util.Hashtable;

import carretti.Carrello;

public class CartServiceImpl implements CartService {

	
	
	Hashtable<String, Integer> cartData = new Hashtable<String, Integer>();
	/*
	 * @param sessionId : identificatore della sessione utente
	 * Simulo operazione DAO per recuperare il carrello dell'utente
	 * 
	 */
	@Override
	public Carrello getCartBySessionId(String sessionId) {
		Integer cartId = cartData.get(sessionId);
		
		/* Simulo dati provenienti da DAO */
		Carrello cart = new Carrello();
		cart.setId(cartId);
		
		return cart;

		
	}

	@Override
	public void setCartBySessionId(Integer cartId,String sessionId) {
	
		cartData.put(sessionId,cartId);
	
	}
	public void populateDummyData() {
		Carrello cart = new Carrello();
		cart.setId(0001);
		setCartBySessionId(cart.getId(), "f543c2ccfa550171f5816c9fdc310165c7e1cc10");
		
	}

}


