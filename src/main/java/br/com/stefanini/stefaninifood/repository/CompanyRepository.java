package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Company;
import br.com.stefanini.stefaninifood.model.OrderedItens;
import br.com.stefanini.stefaninifood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query(value = "SELECT o.id, p.nome, oi.qty,co.id as company_id FROM product p INNER JOIN ordered_itens oi ON oi.product_id = p.id INNER JOIN _order o\n" +
            "ON o.id = oi.order_id INNER JOIN company co ON p.company_id = co.id ;",nativeQuery = true)
    Object[] findOrder();

    @Query(value = "SELECT o.id, p.nome, oi.qty,co.id as company_id FROM product p INNER JOIN ordered_itens oi ON oi.product_id = p.id INNER JOIN _order o\n" +
            "ON o.id = oi.order_id INNER JOIN company co ON p.company_id = co.id\n" +
            "WHERE o.status = 'EM_PREPARACAO' AND co.id = ?1 ;",nativeQuery = true)
    List<Object[]> findReceivedOrdersByCompany(Long id);

    @Query(value = "SELECT co.id FROM product p INNER JOIN ordered_itens oi ON oi.product_id = p.id INNER JOIN _order o\n" +
            "ON o.id = oi.order_id INNER JOIN company co ON p.company_id = co.id\n" +
            "WHERE o.status = 'EM_PREPARACAO' AND p.id = ?1 ;",nativeQuery = true)
    List<Object[]> findId(Long id);

    @Query(value = "SELECT o.id, co.empresa FROM product p INNER JOIN ordered_itens oi ON oi.product_id = p.id INNER JOIN _order o " +
            "ON o.id = oi.order_id INNER JOIN company co ON p.company_id = co.id", nativeQuery = true)
    List<Object[]> findAllOrders();

    @Query(value = "SELECT c.* FROM PRODUCT p inner join company c on c.id = p.company_id where p.id = ?1 ;" , nativeQuery = true)
    Optional<Company> findCompanyByProduct(Long id);

    @Query(value = "SELECT p.* from product p inner join company c on p.company_id = c.id where c.id = ?1; ", nativeQuery = true)
    List<Product> findProductsById(Long id);
//
//    @Query(value = "select c.* from ordered_itens oi inner join product p on oi.product_id = " +
//            "p.id inner join company c on p.company_id = c.id where oi.id = ?1", nativeQuery = true)
//    Company findByOrderId(Long id);

    @Query(value = "select c.empresa, o.id, o.status from company c inner join product p on c.id = p.company_id " +
            " inner join ordered_itens oi on p.id = oi.product_id inner join _order o on oi.order_id = o.id " +
            " where o.status = 'EM_PREPARACAO' AND c.id = ?1 ", nativeQuery = true)
    List<Object[]> findOrdersByCompanyId(Long id);
//    List<Company> findByMenu(true); implementar coluna cardapio boolean em empresa

    @Query(value = "select c.empresa, o.id, o.status from _order o inner join ordered_itens oi on oi.order_id = o.id inner join product p on oi.product_id = p.id inner join company c on c.id = p.company_id where o.status = 'EM_PREPARACAO' and o.consumer_id = ?1 AND c.id = ?2 ; " , nativeQuery = true)
    List<Object[]> groupOrder(Long consumerId, Long companyId);
}
