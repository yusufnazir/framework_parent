package software.simple.solutions.framework.core.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class ActiveDirectoryConnectionUtils {

	public boolean createContext(String url, String username, String password) {
		Hashtable<String, String> env = getProperties(url, username, password);
		boolean valid = false;
		try {
			LdapContext ctx = new InitialLdapContext(env, null);
			if (ctx != null) {
				valid = true;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return valid;
	}

	/**
	 * Ldap server config properties.
	 * 
	 * @param serverUrl
	 * @param user
	 * @param password
	 * @return
	 */
	private Hashtable<String, String> getProperties(String serverUrl, String user, String password) {
		// create an initial directory context
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.REFERRAL, "ignore");
		env.put("com.sun.jndi.ldap.connect.pool", "false");
		/*
		 * environment property to specify how long to wait for a pooled
		 * connection. If you omit this property, the application will wait
		 * indefinitely.
		 */
		env.put("com.sun.jndi.ldap.connect.timeout", "300000");
		env.put(Context.PROVIDER_URL, serverUrl);
		env.put(Context.SECURITY_PRINCIPAL, user);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put("java.naming.ldap.attributes.binary", "tokenGroups objectSid objectGUID");
		return env;
	}
}
