package it.objectmethod.login.dao;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.objectmethod.login.model.User;

@Service
public class UserDao extends NamedParameterJdbcDaoSupport{

	//@Autowired
	//public NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//@Autowired
	//public JdbcTemplate jdbcTemplate;
	
	//@Autowired
	//public DataSource datasource;
	
	public UserDao(DataSource datasource) {
		super();
		setDataSource(datasource);
		}
		
	public Integer update(String nome, String cognome, String email, String password) {
		int rs = 0;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nome", nome);
		params.addValue("cognome", cognome);
		params.addValue("email", email);
		params.addValue("password", password);
		String sql = "INSERT INTO user(nome, cognome, email, password) VALUES (:nome, :cognome, :email, :password);";
		rs = getNamedParameterJdbcTemplate().update(sql, params);
		return rs;
	}
	
	public User getUser(String email, String password) {
		String sql = "Select user.nome as name, user.cognome as surname, user.email, user.password from user where user.email = ? and user.password = ?";
		BeanPropertyRowMapper<User> rm = new BeanPropertyRowMapper<User>(User.class);
		User user = getJdbcTemplate().queryForObject(sql, new Object[] {email,password}, rm);
		return user;	
	}
	
	public List<User> getUserByEmail(String email) {
		List<User> list = new ArrayList<User>();
		String sql = "Select * from user where user.email = ?";
		BeanPropertyRowMapper<User> rm = new BeanPropertyRowMapper<User>(User.class);
		list = getJdbcTemplate().query(sql, new Object[] {email}, rm);
		return list;	
	}

}
