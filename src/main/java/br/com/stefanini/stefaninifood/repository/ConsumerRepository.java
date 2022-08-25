package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer,Long> {

    @Override
    @Query(value = "select * from consumer where active = true",nativeQuery = true)
    List<Consumer> findAll();

    @Query(value = "select * from consumer where active = true",nativeQuery = true)
    Optional<Consumer> findByCpf(String cpf);

    @Query(value = "select * from consumer where active = false", nativeQuery = true)
    Optional<Consumer> findDeactivatedByCpf(String cpf);

    void deleteByCpf(String cpf);
}
