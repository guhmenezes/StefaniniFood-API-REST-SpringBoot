package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.model.Order;
import br.com.stefanini.stefaninifood.model.OrderedItens;
import br.com.stefanini.stefaninifood.model.Product;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import br.com.stefanini.stefaninifood.repository.OrderRepository;
import br.com.stefanini.stefaninifood.repository.ProductRepository;

import java.math.BigInteger;
import java.util.List;

import static java.util.Objects.isNull;

public class OrderedItensRequest {

    private Long consumerId;
    private Long productId;
    private Long orderId;
    private Integer qty;

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public OrderedItens toModel(ConsumerRepository cr, ProductRepository pr, OrderRepository or){
        Consumer consumer = cr.findById(consumerId).get();
        Product product = pr.findById(productId).get();
        Order order = null;
        try {
            List<Object[]> openOrderId = or.findOpenOrder(consumerId);
            BigInteger orderId = (BigInteger) openOrderId.get(0)[0];
            String oId = orderId.toString();
            order = or.findById(Long.parseLong(oId)).get();
        } catch (Exception e) {
            order = new Order(consumer);
            or.save(order);

        } finally {
            OrderedItens orderedItens = new OrderedItens(qty,order,product);
            order.addItem(orderedItens);
            if (!isNull(order.getTotal())){
                order.setTotal(order.getTotal() + (qty * orderedItens.getProduct().getPrice()));
            } else {
                order.setTotal(qty * orderedItens.getProduct().getPrice());
            }
            or.save(order);
            return orderedItens;
        }

    }

    @Override
    public String toString() {
        return "OrderedItensRequest{" +
                "consumerId=" + consumerId +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", qty=" + qty +
                '}';
    }

    public Order toDatabase(ProductRepository pr, Order order, OrderRepository or) {
        Product product = pr.findById(productId).get();
        or.save(order);
        OrderedItens orderedItens = new OrderedItens(qty, order, product);
        order.addItem(orderedItens);
        order.setTotal(qty * orderedItens.getProduct().getPrice());
        or.save(order);
        return order;
    }

    //    public OrderedItens update(OrderedItensRepository repository){
//        repository.findById(id);
//        return orderedItens;
//    }

}
