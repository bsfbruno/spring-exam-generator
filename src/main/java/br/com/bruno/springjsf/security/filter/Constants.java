package br.com.bruno.springjsf.security.filter;

import java.util.concurrent.TimeUnit;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Constants {

	public static final String SECRET = "secret";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final long EXPIRATION_TIME = 86400000; //1 day
	
	public static void main(String[] args) {
		//converting 1 day to milliseconsds
		System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		System.out.println(new BCryptPasswordEncoder().encode("senhamestre"));
	}
}
