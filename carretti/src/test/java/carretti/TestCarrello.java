package carretti;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import valueobject.Prodotto;

public class TestCarrello {
	private Shop shop;
	private Carrello carrello;
	private static Prodotto P1, P2, P3, P4, P5;
	static {
		P1 = new Prodotto("FAKE", "NAME", 10F);
		P2 = new Prodotto("A01", "Bottiglietta Ferrarelle", .5F);
		P3 = new Prodotto("A02", "Computer asus", 1000F);
		P4 = new Prodotto("A03", "Cuffie cellulare", 20.5F);
		P5 = new Prodotto("A04", "Mouse TRUST", 12F);
	}
	
	@Before
	public void initShop() {
		shop = new Shop();
		carrello = new Carrello();
	}
	
	@After
	public void destroyShop() {
		shop = null;
		carrello = null;
	}
	
	private final Set<Prodotto> initProducts(final Prodotto ...products) {
		Set<Prodotto> prod = new HashSet<Prodotto>();
		for(final Prodotto p : products)
			prod.add(p);
		return prod;
	}
	
	/**
	 * tenta di rimuovere un prodotto da un carrello vuoto
	 * @throws Exception 
	 */
	@Test
	public void testRemoveProdEmptyCart() {
		try {
			carrello.removeByCodice("TEST_1", 1);
			fail("removing not possible");
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * prova ad inserire un prodotto che non esiste nello Shop
	 * @throws Exception 
	 */
	@Test
	public void testAddProductNotExist() {
		try {
			carrello.addByCodice(P1.getCodice(), 10);
			fail("il prodotto aggiunto non esiste");
		} catch (Exception e) {
			//intentionally empty
		}
	}
	
	/**
	 * prova ad inserire prodotti presenti e prodotti che non
	 * sono presenti nello shop
	 * 
	 */
//	@Test
	public void testAddProducts() {
		Set<Prodotto> prods = initProducts(P1,P2,P3,P4,P5);
		for(Prodotto p : prods)
			try {
				carrello.addByCodice(p.getCodice(), 10);
			} catch (Exception e) {
				//intentionally empty
			}
		HashMap<String, Prodotto> expected = new HashMap<String, Prodotto>();
		expected.put(P2.getCodice(), P2);
		
	}
}
