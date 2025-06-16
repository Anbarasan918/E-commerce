package com.application.security;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.application.Utils.ApplicationConstants;
import com.application.entity.UserData;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class JWTUtil {
 
    
    private static MyUserDetailsService userDetails;
    
    @Autowired()
    public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
    	JWTUtil.userDetails = myUserDetailsService;
    }

	
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

	
	public static  Long extractIdFromToken(HttpServletRequest httpRequest) {
		String token = getJwtTokenFromCookies(httpRequest, "jwtToken");
		Optional<UserData> userRes = Optional.empty();
        if (token == null) {
            throw new RuntimeException("JWT Token not found in cookies");
        }

        String[] parts = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> claims = null;
		try {
			claims = mapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
			String email = String.valueOf(claims.get("email"));
			userRes = userDetails.getUserDetails(email);
   
			
			if (userRes.isPresent()) {
                return userRes.get().getId();
            } else {
                throw new RuntimeException("User not found for email: " + email);
            }
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return userRes.get().getId();

        
	}
}
