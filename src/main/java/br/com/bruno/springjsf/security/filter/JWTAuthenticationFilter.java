package br.com.bruno.springjsf.security.filter;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bruno.springjsf.persistence.model.ApplicationUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static br.com.bruno.springjsf.security.filter.Constants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager)	{
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		ZonedDateTime expecTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);
		String token = Jwts.builder()
				.setSubject(((ApplicationUser) auth.getPrincipal()).getUsername())
				.setExpiration(Date.from(expecTimeUTC.toInstant()))
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
		token = TOKEN_PREFIX + token;
		String tokenJson = "{\"token\":" + addQuotes(token) + ",\"exp\":"+addQuotes(expecTimeUTC.toString())+"}";
		response.getWriter().write(tokenJson);
		response.addHeader("Content-Type", "application/json;charset=UTF-8");
		response.addHeader(HEADER_STRING, token);
	}
	
	private String addQuotes(String value) {
		return new StringBuilder(300).append("\"").append(value).append("\"").toString();
	}
}
