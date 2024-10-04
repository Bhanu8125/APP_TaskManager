package org.springboot.taskmanager.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springboot.taskmanager.repository.UsersRepository;
import org.springboot.taskmanager.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

public class UserAuthenticationFilter extends AuthenticationFilter {

    public UserAuthenticationFilter(TokenService tokenService) {
        super(new UserAuthenticationManager(tokenService), new BearerAuthenticationTokenConverter());
        this.setSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }



    static class BearerAuthenticationTokenConverter implements AuthenticationConverter{

        @Override
        public Authentication convert(HttpServletRequest request) {
            System.out.println("Authentication Header in Converter " + request.getHeader("Authorization"));
            if(request.getHeader("Authorization") !=null){
                String token = request.getHeader("Authorization").replace("Bearer ", "");
                System.out.println("Authentication Header inside converter authenticate method " + token);

                return new UserAuthentication(token);
            }

            return null;
        }
    }

    @Component
    static class UserAuthenticationManager implements AuthenticationManager {
        private final TokenService tokenService;

        @Autowired
        private  UsersRepository usersRepository;

        UserAuthenticationManager(TokenService tokenService) {
            this.tokenService = tokenService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            System.out.println("Authentication Principal in Manager " + authentication.getPrincipal());
            if(authentication instanceof UserAuthentication){
                var userAuthentication = (UserAuthentication) authentication;
                //var isTokenvalid = tokenService.validateToken(userAuthentication.getCredentials().toString(), usersRepository);
                var username = tokenService.getUserNameFromToken(userAuthentication.getCredentials().toString());
                if(username != null){
                    System.out.println("userName inside If Block of Manager: " + username);
                    userAuthentication.setUser(username);
                    return userAuthentication;
                }
                else
                    throw new AuthenticationException("Invalid token") {};
            }
            return null;
        }
    }
}
