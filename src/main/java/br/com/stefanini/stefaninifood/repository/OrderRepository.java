package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long> {

    List<Order> findByConsumerId(Long id);

    @Query(value = "select * from _order where status = 'AGUARDANDO' AND consumer_id = ?1", nativeQuery = true)
    List<Order> findWaitingOrdersByConsumerId(Long id);


//    List<Order> findByCompany
    @Query(value = "select o.id, " +
            "p.company_id from _order o " +
            "inner join ordered_itens oi on oi.order_id = o.id " +
            "inner join product p on p.id = oi.product_id " +
            "where consumer_id = ?1 and o.status = 'AGUARDANDO' " +
            "order by o.bought_at desc", nativeQuery = true)
    List<Object[]> findOpenOrder(Long id);

//    @Query(value = "select o.id, " +
//            "p.company_id from _order o " +
//            "inner join ordered_itens oi on oi.order_id = o.id " +
//            "inner join product p on p.id = oi.product_id " +
//            "where consumer_id = ?1 and o.status = 'AGUARDANDO' " +
//            "order by o.bought_at desc", nativeQuery = true)
    List<Order> findByProductsId(Long id);

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

    @Modifying
    @Query(value = "delete from _order where status = 'AGUARDANDO' and consumer_id = ?1 ;", nativeQuery = true)
    void deleteByConsumerId(Long id);

}
