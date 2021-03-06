package services;

import carretti.Carrello;

public interface CartService {

	public Carrello getCartBySessionId(String sessionId) throws Exception;

	public void setCartBySessionId(Carrello cart, String sessionId);
}
