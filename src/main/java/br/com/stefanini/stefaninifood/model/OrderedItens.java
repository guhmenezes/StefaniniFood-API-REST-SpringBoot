package br.com.stefanini.stefaninifood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class OrderedItens {
    @Id
    @GeneratedValue
    private Long id;
    private Double unitPrice;
    private Integer qty;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;
    @JsonIgnore
    @ManyToOne
    private Product product;

    public OrderedItens() {
    }

    public OrderedItens(Integer qty, Order order, Product product) {
        this.qty = qty;
        this.order = order;
        this.unitPrice = product.getPrice();
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getTotal(){
        return this.unitPrice * this.qty;
    }

    @Override
    public String toString() {
        return "{" +
                "Pedido Nº: " + this.getOrder().getId() +
                ", Cliente: " + this.getOrder().getConsumer().getName() +
                ", Produto: " + this.getProduct().getName() +
                ", Status: " + this.getOrder().getStatus() +
                "}";
    }
}
