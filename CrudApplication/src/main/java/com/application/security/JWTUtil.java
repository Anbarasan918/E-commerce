package com.application.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.application.Pojo.User;

import com.application.Repository.UserRepo;
import com.application.Utils.ApplicationConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;



@Component
public class JWTUtil {

	@Value("${jwt_secret}")
	private String secret;
	
	public String generateToken(String email) throws JWTCreationException, IllegalArgumentException {

		return JWT.create().withSubject("User Detail").withClaim("email", email).withIssuedAt(new Date())
				.withIssuer(ApplicationConstants.ECOMMERCE_APPLICATION).sign(Algorithm.HMAC256(secret));
	}
	
	
	public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
		
		DecodedJWT jwtVerifier = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withSubject("User Detail")
					.withIssuer(ApplicationConstants.ECOMMERCE_APPLICATION).build();

			 jwtVerifier = verifier.verify(token);
			
		} catch (Exception e) {
			System.out.println("error at  validateTokenAndRetrieveSubject =>" + e.getMessage());
		}
		return jwtVerifier.getClaim("email").asString();
	}
	
	
	public static String getJwtTokenFromCookies(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue(); // Return the JWT token
                }
            }
        }
        return null; // Return null if token is not found
    }
}
