package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface ConsumerRepository extends JpaRepository<Consumer,Long> {
    List<Consumer> findByCpf(String cpf);
}
