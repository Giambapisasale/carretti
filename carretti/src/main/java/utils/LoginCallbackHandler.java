package utils;
import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class LoginCallbackHandler implements CallbackHandler {
	private String userName;
	private String password;

	/**
	 * @param user
	 * @param pass
	 */
	public LoginCallbackHandler(String user , String pass){
	    this.userName = user;
	    this.password = pass;
	}
	
	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                if (userName != null) {
                    ((NameCallback)callbacks[i]).setName(userName);
                }
            }
            else if (callbacks[i] instanceof PasswordCallback) {
                if (password != null) {
                    ((PasswordCallback)callbacks[i]).setPassword(password.toCharArray());
                }
            }
            else {
                throw new UnsupportedCallbackException(callbacks[i], "The submitted Callback is unsupported");
            }
        }
	}

}
