package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
