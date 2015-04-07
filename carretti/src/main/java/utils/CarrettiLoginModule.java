package utils;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class CarrettiLoginModule implements LoginModule{
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map sharedState;
	private Map options;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
		
	}

	@Override
	public boolean login() throws LoginException {
		
		boolean returnValue = false;
		
		System.out.println("Login Call");

		
		
		if(callbackHandler == null){
			throw new LoginException("No callback handler supplied.");
		}

		Callback[] callbacks = new Callback[2];
		callbacks[0] = new NameCallback("Username");
		callbacks[1] = new PasswordCallback("Password", false);

		try {
			System.out.println("TRY");

			callbackHandler.handle(callbacks);
			String userName = ((NameCallback)callbacks[0]).getName();
			char [] passwordCharArray = ((PasswordCallback)callbacks[1]).getPassword();
			String password = new String(passwordCharArray);
			
			System.out.println("*****username:"+userName);
			System.out.println("*****password:"+password);

			
	        //==> Authentication.
			returnValue = userName.equals(password);
			System.out.println("*****returnValue:"+returnValue);
			if(returnValue ) {
				System.out.println("Success! You get to log in!");
				returnValue = true;
				return true;
			} else {
				System.out.println("Failure! You don't get to log in");
				returnValue = false;
				return returnValue;
				//throw new FailedLoginException("Sorry! No login for you.");
			}


		} catch (IOException ioe) {
			System.out.println("*****catch1");
			ioe.printStackTrace();
			throw new LoginException("IOException occured.");
		} catch (UnsupportedCallbackException e) {
			System.out.println("*****catch2");
			e.printStackTrace();
			throw new LoginException("UnsupportedCallbackException encountered.");
		}
	  
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

}
