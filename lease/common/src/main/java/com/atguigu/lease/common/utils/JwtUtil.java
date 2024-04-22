package com.atguigu.lease.common.utils;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * ClassName:JwtUtil
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/22 18:49
 * @Version 1.0
 */
public class JwtUtil {

   private static long tokenExpiration = 60 * 60 * 1000L;
   private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());

   public static String createToken(Long userId, String username) {
      String token = Jwts.builder().
              setSubject("USER_INFO").
              setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).
              claim("userId", userId).
              claim("username", username).
              signWith(tokenSignKey).
              compressWith(CompressionCodecs.GZIP).
              compact();
      return token;
   }

   public static Claims parseToken(String token) {
      try {
         Jws<Claims> claimsJws = Jwts.parserBuilder().
                 setSigningKey(tokenSignKey).
                 build().parseClaimsJws(token);
         return claimsJws.getBody();

      } catch (ExpiredJwtException e) {
         throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
      } catch (JwtException e) {
         throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
      }
   }
}