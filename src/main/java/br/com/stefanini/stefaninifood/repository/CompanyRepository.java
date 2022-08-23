package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
//    List<Company> findByMenu(true); implementar coluna cardapio boolean em empresa
}
