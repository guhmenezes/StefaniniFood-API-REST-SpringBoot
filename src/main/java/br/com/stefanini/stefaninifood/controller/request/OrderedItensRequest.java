package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.model.Order;
import br.com.stefanini.stefaninifood.model.OrderedItens;
import br.com.stefanini.stefaninifood.model.Product;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import br.com.stefanini.stefaninifood.repository.OrderRepository;
import br.com.stefanini.stefaninifood.repository.ProductRepository;

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
        Order order = new Order(consumer);
        or.save(order);
        OrderedItens orderedItens = new OrderedItens(qty,order,product);
        order.addItem(orderedItens);
        return orderedItens;
    }

}
