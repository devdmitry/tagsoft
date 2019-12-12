package com.rohov.tagsoft.security.filter;

import com.rohov.tagsoft.entity.User;
import com.rohov.tagsoft.security.TokenProvider;
import com.rohov.tagsoft.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorizationFilter extends BasicAuthenticationFilter {

    TokenProvider tokenProvider;
    UserService userService;

    public AuthorizationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider,
                               UserService userService) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = getAuthentication(request);

        if (auth == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            chain.doFilter(request,response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isNotEmpty(token) && token.startsWith(tokenProvider.getTOKEN_PREFIX())) {
            try {

                byte[] signingKey = tokenProvider.getSecretKey().getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace(tokenProvider.getTOKEN_PREFIX(), ""));

                String email = parsedToken.getBody().getSubject();

                User user = (User) userService.loadUserByUsername(email);

                if (Strings.isNotEmpty(email)) {
                    return new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);
                }
            } catch (RuntimeException exception) {
                log.warn("Error : {} - {}", token, exception.getMessage());
            }
        }

        return null;
    }
}
