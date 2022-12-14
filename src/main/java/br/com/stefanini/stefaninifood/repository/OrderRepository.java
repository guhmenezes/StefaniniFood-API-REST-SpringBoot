package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Order;
import br.com.stefanini.stefaninifood.model.OrderedItens;
import br.com.stefanini.stefaninifood.vo.ReceivedOrderVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long> {

    List<Order> findByConsumerId(Long id);

//    List<Order> findByCompany

    @Query(value = "SELECT o.id," +
            "            p.nome," +
            "            o.total," +
            "            co.empresa" +
            "            FROM product p" +
            "            INNER JOIN ordered_itens oi" +
            "            ON oi.product_id = p.id" +
            "            INNER JOIN _order o" +
            "           ON o.id = oi.order_id" +
            "           INNER JOIN company co" +
            "           ON p.company_id = co.id" +
            "           WHERE o.status = 'FINALIZADO' AND co.id = ?1 ;",nativeQuery = true)
    List<Object[]> findCompletedOrders(Long id);

    @Query(value = "SELECT o.id," +
            "            p.nome," +
            "            o.total," +
            "            co.empresa" +
            "            FROM product p" +
            "            INNER JOIN ordered_itens oi" +
            "            ON oi.product_id = p.id" +
            "            INNER JOIN _order o" +
            "           ON o.id = oi.order_id" +
            "           INNER JOIN company co" +
            "           ON p.company_id = co.id" +
            "           WHERE o.id = ?1 ;",nativeQuery = true)
    List<Object[]> findOrders(BigInteger id);

}
