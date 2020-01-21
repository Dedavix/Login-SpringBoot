package it.objectmethod.login.controller.rest;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.login.Dao.UserDao;
import it.objectmethod.login.auth.tables.AuthenticateTable;
import it.objectmethod.login.model.User;
import it.objectmethod.login.model.enums.LoginStatusMsg;

@RestController
@RequestMapping("/api/user")
public class LoginRestController {
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public AuthenticateTable tokenTable;
	
	@PostMapping("/signin")
	public ResponseEntity<String> getUser(@RequestParam(value = "email",required = true) String email, @RequestParam(value = "password", required= true) String password) {
		User user = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		String statusMsg;
		user = userDao.getUser(email, password);
		if (user== null) {
			user = new User();
			 statusMsg=LoginStatusMsg.KO_LOGIN.toString();
		} else {
			String uniqueID = UUID.randomUUID().toString();
			tokenTable.getAuthTable().put(uniqueID, user);
			responseHeaders.set("token", uniqueID);
			statusMsg = LoginStatusMsg.OK_LOGIN.toString();
		}
		return ResponseEntity.ok()
			      .headers(responseHeaders)
			      .body(statusMsg);	
	}
	
	@PostMapping("/save")
	public String registerUser(@RequestBody User user) {
		String name = user.getName();
		String surname = user.getSurname();
		String email= user.getEmail();
		String password = user.getPassword();
		String outputMsg = null;
		
		if(name == null || surname == null || email == null || password == null) {
			outputMsg = LoginStatusMsg.ERR_REGISTRAZIONE.toString();
		}else {
			List<User> userList = userDao.getUserByEmail(email);
			if (!userList.isEmpty()) {
				outputMsg = LoginStatusMsg.KO_REGISTRAZIONE.toString();
			} else {
				int rs = userDao.update(name, surname, email, password);
				if(rs > 0) {
					outputMsg = LoginStatusMsg.OK_REGITRAZIONE.toString();
				}else {
					outputMsg = LoginStatusMsg.ERR_REGISTRAZIONE.toString();
				}		
			}	
		}	
		return outputMsg;	
	}
	
	@GetMapping("/getNome")
	public String getNome(@RequestHeader(value="token")String token) {
		
		User user = tokenTable.getAuthTable().get(token);
		
		return "Ciao " + user.getName();
		
	}

}
