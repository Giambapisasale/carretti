package carretti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import valueobject.Response;

public final class TestSessione {
	private final static String USER_1 = "user1";
	private final static String USER_2 = "user2";
	private final static String FAKE_USER = "fake_user";
	private final static String PWD = "password";
	private Server server;
	
	@Before
	public void initServer() {
		server = new Server();
	}
	
	@After
	public void destroyServer() {
		server = null;
	}
	
	/**
	 * credenziali corretti
	 */
	@Test
	public void testLoginCorrect() {
		Response actual = server.login(USER_1, PWD);
		System.err.println(actual);
		assertTrue(actual.getEsito());
	}
	
	/**
	 * credenziali errate
	 */
//	@Test
	public void testLoginNotCorrect() {
		Response actual = server.login(FAKE_USER, PWD);
		assertFalse(actual.getEsito());
	}
	
	/**
	 * due accessi dello stesso utente devono restituire lo stesso <b>user id</b>
	 */
	@Test
	public void testSameUserId() {
		Response expected = server.login(USER_1, PWD);
		//Response actual = server.login(USER_1, PWD);
		System.err.println(expected.getSessionCode());
//		System.err.println(actual.getSessionCode());
		System.err.println(expected);
		assertFalse(expected.getSessionCode().isEmpty());
	}
}
