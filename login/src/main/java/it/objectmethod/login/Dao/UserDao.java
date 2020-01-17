package it.objectmethod.login.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import it.objectmethod.login.model.User;

@Service
public class UserDao extends NamedParameterJdbcDaoSupport{

	@Autowired
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
		
	public Integer update(String nome, String cognome, String email, String password) {
		int rs = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nome", nome);
		params.addValue("cognome", cognome);
		params.addValue("email", email);
		params.addValue("password", password);
		String sql = "INSERT INTO user(nome, cognome, email, password) VALUES (:name, :surname, :email, :password);";
		rs = namedParameterJdbcTemplate.update(sql, params);
		return rs;
	}
	
	public User getUser(String email, String password) {
		String sql = "Select * from user where user.email = ? and user.password = ?";
		BeanPropertyRowMapper<User> rm = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, new Object[] {email,password}, rm);
		return user;	
	}
	
	public User getUserByEmail(String email) {
		String sql = "Select * from user where user.email = ?";
		BeanPropertyRowMapper<User> rm = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, new Object[] {email}, rm);
		return user;	
	}

}
