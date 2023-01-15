package br.com.stefanini.stefaninifood.repository;

import br.com.stefanini.stefaninifood.model.OrderedItens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderedItensRepository extends JpaRepository<OrderedItens,Long> {
    @Query(value = "SELECT o.id," +
            "            p.nome," +
            "            oi.unit_price," +
            "            oi.qty," +
            "            o.total," +
            "            o.status," +
            "            c.empresa" +
            "            FROM company c" +
            "            INNER JOIN product p" +
            "            ON p.company_id=c.id " +
            "            INNER JOIN ordered_itens oi" +
            "            ON oi.product_id = p.id" +
            "            INNER JOIN _order o" +
            "           ON o.id = oi.order_id" +
            "           WHERE o.consumer_id = ?1 ;", nativeQuery = true)
    List<Object[]> findAllByConsumerId(Long id);

    @Query(value = "SELECT o.id," +
            "            p.nome," +
            "            oi.unit_price," +
            "            oi.qty," +
            "            o.total," +
            "            o.status," +
            "            c.empresa," +
            "            c.id as company_id," +
            "            p.id as product_id," +
            "            oi.id as item_id" +
            "            FROM company c" +
            "            INNER JOIN product p" +
            "            ON p.company_id=c.id " +
            "            INNER JOIN ordered_itens oi" +
            "            ON oi.product_id = p.id" +
            "            INNER JOIN _order o" +
            "           ON o.id = oi.order_id" +
            "           WHERE o.status = 'AGUARDANDO' AND o.consumer_id = ?1 ;", nativeQuery = true)
    List<Object[]> findCartByConsumerId(Long id);


    @Query(value = "SELECT oi.*" +
            "            FROM product p" +
            "            INNER JOIN ordered_itens oi" +
            "            ON oi.product_id = p.id" +
            "            INNER JOIN _order o" +
            "           ON o.id = oi.order_id" +
            "           WHERE o.status = 'EM_PREPARACAO' AND o.consumer_id = ?1 ;", nativeQuery = true)
    List<OrderedItens> findBoughtByConsumerId(Long id);

    @Query(value = "select o.id, o.status, o.consumer_id, o.bought_at, oi.qty, " +
            "p.nome, p.id as product_id, c.empresa , c.id as company_id from _order o inner join ordered_itens oi on oi.order_id = o.id " +
            "inner join product p on p.id = oi.product_id inner join company c on c.id = p.company_id " +
            "where o.status <> 'AGUARDANDO' AND o.consumer_id = ?1 " +
            "order by o.bought_at desc", nativeQuery = true)
    List<Object[]> findAllOrdersByConsumerId(Long id);


    @Query(value = "    select oi.id, " +
            "           oi.qty, " +
            "           oi.unit_price, " +
            "           oi.order_id, " +
            "           oi.product_id " +
            "           FROM ordered_itens oi" +
            "           INNER JOIN product p" +
            "           ON oi.product_id = p.id" +
            "           INNER JOIN _order o" +
            "           ON oi.order_id = o.id" +
            "           WHERE o.status = 'EM_PREPARACAO' AND" +
            "           p.company_id = ?1 ;", nativeQuery = true)
    List<OrderedItens> findByCompanyId(Long id);

    @Query(value = "    select oi.id, " +
            "           oi.qty, " +
            "           oi.unit_price, " +
            "           oi.order_id, " +
            "           oi.product_id " +
            "           FROM ordered_itens oi" +
            "           INNER JOIN product p" +
            "           ON oi.product_id = p.id" +
            "           INNER JOIN _order o" +
            "           ON oi.order_id = o.id" +
            "           WHERE p.company_id = ?1 ;", nativeQuery = true)
    List<OrderedItens> findOrdersByCompanyId(Long id);

    @Query(value = "select order_id from ordered_itens where id = ?1", nativeQuery = true)
    Long orderId(Long itemId);

    @Query(value = "select count(*) from ordered_itens where order_id = (\n" +
            "select o.id from _order o left join ordered_itens oi on o.id = oi.order_id where oi.id = ?1);", nativeQuery = true)
    Integer orderedItensSize(Long id);

}
