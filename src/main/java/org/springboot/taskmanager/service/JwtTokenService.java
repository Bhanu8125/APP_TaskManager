package org.springboot.taskmanager.service;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.User;
import org.springboot.taskmanager.model.Users;
import org.springboot.taskmanager.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenService implements TokenService {

    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.token.expiration}")
    private Long expirationTimeInMs;

    @Value("${jwt.token.issuer")
    private String issuer;

    private SecretKey secretKey;

    public JwtTokenService() {
    }

    @PostConstruct
    public void init(){
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = new SecretKeySpec(decodedKey,0,decodedKey.length,"HmacSHA256");
        System.out.println("SecretKey in INIT Method :" + this.secretKey);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("expiration", expirationTimeInMs);
        claims.put("subject", user.getUsername());

        String token =  createAuthToken(claims, user.getUsername());
        System.out.println("Claims in TokenService :" + claims);
        return token;
    }


    public String createAuthToken(Map<String, Object> claims, String username) {
        System.out.println("SecretKey inside CreateAuthTokem :" + secretKey);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMs))
                .setIssuer(issuer)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UsersRepository usersRepository) {
        final String username = getUserNameFromToken(token);
        Users user = usersRepository.findByUsername(username);
        return (user != null && user.getUsername().equalsIgnoreCase(username) && isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claim = extractAllClaims(token);
        return claimsResolver.apply(claim);
    }

    public Claims extractAllClaims(String token) {
        System.out.println("The secretkey :" + this.secretKey);
        return Jwts.parser().
                setSigningKey(this.secretKey).
                parseClaimsJws(token).
                getBody();
    }

//    @Override
//    public String createAuthToken(String username) {
//        return Jwts.builder()
//                .setIssuer("api/task")
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+expirationTimeInMs))
//                .setSubject(username)
//                .toString();
//    }
//
//    @Override
//    public String getUserNameFromToken(String token) {
//        return "";
//    }
}
