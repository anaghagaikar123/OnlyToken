package com.example.demo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {

	static final long EXPIRATIONTIME = 864_000_000;// 10 Days
	static final String SECRET = "ThisIsSecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	public static void addAuthentication(HttpServletResponse response, Authentication authResult) {
		Claims claims = Jwts.claims().setSubject(authResult.getName());
		claims.put("scopes", authResult.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
		String JWT = Jwts.builder().setClaims(claims)
				// .setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		
		//JSONObject jsonResponse = new JSONObject();
		
		try {
			response.setContentType("application/json");
			response.getWriter().append("{\"Authentication\": "+ "\""+ TOKEN_PREFIX + " " + JWT + "\"" + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
			String user = claims.getSubject();
			List<String> scopes = claims.get("scopes", List.class);
			List<GrantedAuthority> authorities = scopes.stream().map(authority -> new SimpleGrantedAuthority(authority))
					.collect(Collectors.toList());
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, authorities) : null;

		}
		return null;
	}

}