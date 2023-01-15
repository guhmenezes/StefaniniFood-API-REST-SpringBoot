package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    public Address findByIdAndConsumerId(Long idAddress, Long idConsumer);

    @Query(value = "select count(*) from address where consumer_id = ?1 and cep = ?2 and numero = ?3", nativeQuery = true)
    public Integer hasRegistry(Long idConsumer, String cep, Integer numero);

    @Query(value = "select id from address where consumer_id = ?1 and cep = ?2 and numero = ?3", nativeQuery = true)
    public String getId(Long idConsumer, String cep, Integer numero);
}
