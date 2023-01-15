package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.config.security.TokenService;
import br.com.stefanini.stefaninifood.controller.dto.TokenDTO;
import br.com.stefanini.stefaninifood.controller.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid LoginRequest request){
        UsernamePasswordAuthenticationToken loginData = request.converter();
        try {
            Authentication authentication = authManager.authenticate(loginData);
            String token = tokenService.generateToken(authentication);
            String id = tokenService.getUserId(token);
            return ResponseEntity.status(HttpStatus.OK).body(new TokenDTO(token, "Bearer", id));
        } catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
