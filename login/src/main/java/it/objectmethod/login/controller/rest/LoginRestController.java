package it.objectmethod.login.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.login.Dao.UserDao;
import it.objectmethod.login.model.User;
import it.objectmethod.login.model.enums.LoginStatusMsg;

@RestController
@RequestMapping("/api/user")
public class LoginRestController {
	
	@Autowired
	public UserDao userDao;
	
	@GetMapping("/getByEmailAndPassword")
	public User getUser(@RequestParam(value = "email",required = true) String email, @RequestParam(value = "password", required= true) String password) {
		User user = null;
		user = userDao.getUser(email, password);
		if (user== null) {
			user = new User();
			user.setLoginStatus(LoginStatusMsg.KO_LOGIN);
		}
		return user;	
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
			User userName = userDao.getUserByEmail(email);
			if (userName != null) {
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

}
