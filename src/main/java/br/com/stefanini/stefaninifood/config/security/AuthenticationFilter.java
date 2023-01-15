package br.com.stefanini.stefaninifood.config.security;

import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private ConsumerRepository repository;

    public AuthenticationFilter(TokenService tokenService, ConsumerRepository repository){
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        boolean valid = tokenService.isTokenValid(token);
        if(valid){
            authenticateUser(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        String username = tokenService.getUserId(token);
        Consumer consumer = repository.findByEmail(username).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(consumer, null, consumer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7);
    }
}
