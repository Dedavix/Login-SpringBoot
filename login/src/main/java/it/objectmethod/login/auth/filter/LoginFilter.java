package it.objectmethod.login.auth.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.objectmethod.login.auth.tables.AuthenticateTable;

@Component
public class LoginFilter implements Filter {

	@Autowired
	public AuthenticateTable authTable;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getRequestURI();
		if (uri.endsWith("/signin") || uri.endsWith("/save")) {
			chain.doFilter(request, response);
		} else if (httpRequest.getRequestURI().endsWith("/getNome")) {
			String token = httpRequest.getHeader("token");

			if (token == null || !authTable.getAuthTable().containsKey(token)) {
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Filter.super.destroy();
	}
}
