package it.objectmethod.login.auth.tables;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.objectmethod.login.model.User;

@Component
public class AuthenticateTable {
	
	private Map<String, User> authTable;

	public Map<String, User> getAuthTable() {
		if(this.authTable==null) {
			authTable = new HashMap<String, User>();
		}
		return authTable;
	}
		
}
