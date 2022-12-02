package com.example.util;

import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.exp.ItemNotFoundException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "topsecretKey!123";

    public static String encode(Integer profileId, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);

        int tokenLiveTime = 1000 * 3600 * 24; // 1-day
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Mazgi");

        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            Integer id = (Integer) claims.get("id");

            String role = (String) claims.get("role");
            ProfileRole profileRole = ProfileRole.valueOf(role);

            return new JwtDTO(id, profileRole);

        } catch (SignatureException e) {
            // so("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            //  logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (JwtException e) {
            //
        }
        return null;
    }


    public static String getTokenFromHeader(String header) {
        if (!(header.startsWith("Bearer "))) {
            throw new ItemNotFoundException("Token Not exists");
        }
        String[] array = header.split(" ");
        if (array.length != 2) {
            throw new RuntimeException("Token Not exists");
        }
        return array[1].trim();
    }


}
