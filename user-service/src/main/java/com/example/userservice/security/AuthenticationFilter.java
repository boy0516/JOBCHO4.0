package com.example.userservice.security;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@NoArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment env){
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env= env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try{
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );

        }catch(IOException e){
            throw  new RuntimeException(e);
        }



    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String userEmail = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDetailsByUserEmail(userEmail);
        String accessToken = Jwts.builder()
                .setSubject(userDto.getUserEmail())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

//        String refreshToken = Jwts.builder()
//                .setSubject(userDto.getUserEmail())
//                .setExpiration(new Date(System.currentTimeMillis() +
//                        Long.parseLong(env.getProperty("refresh_token.expiration_time"))))
//                .signWith(SignatureAlgorithm.HS512, env.getProperty("refresh_token.secret"))
//                .compact();

        response.addHeader("Token", accessToken);

        Cookie cookie = new Cookie("accessToken",accessToken);
        cookie.setPath("/");
        cookie.setDomain("127.0.0.1");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.addHeader("userNum", String.valueOf(userDto.getUserNum()));
        response.addHeader("userEmail", userDto.getUserEmail());
    }
}

