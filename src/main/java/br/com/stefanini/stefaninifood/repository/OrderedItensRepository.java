package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.OrderedItens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface OrderedItensRepository extends JpaRepository<OrderedItens,Long> {
    @Query(value = "SELECT o.id," +
            "            p.nome," +
            "            oi.unit_price," +
            "            oi.qty," +
            "            o.total," +
            "            o.status" +
            "            FROM product p" +
            "            INNER JOIN ordered_itens oi" +
            "            ON oi.product_id = p.id" +
            "            INNER JOIN _order o" +
            "           ON o.id = oi.order_id" +
            "           WHERE o.consumer_id = ?1 ;", nativeQuery = true)
    List<Object[]> findByConsumerId(Long id);

    @Query(value = "    select oi.id, " +
            "           oi.qty, " +
            "           oi.unit_price, " +
            "           oi.order_id, " +
            "           oi.product_id " +
            "           FROM ordered_itens oi" +
            "           INNER JOIN product p" +
            "           ON oi.product_id = p.id" +
            "           WHERE p.company_id = ?1 ;", nativeQuery = true)
    List<OrderedItens> findByCompanyId(Long id);
}
