package br.com.stefanini.stefaninifood.config.security;

import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    ConsumerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Consumer> user = repository.findByEmail(username);
        if(user.isPresent()){
            return user.get();
        }

        throw new UsernameNotFoundException("Dados inválidos");
    }
}
