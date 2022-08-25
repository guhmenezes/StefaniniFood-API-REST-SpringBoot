package br.com.stefanini.stefaninifood.model;

import javax.persistence.*;

@Entity
public class OrderedItens {
//    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private Double unitPrice;
    private Integer qty;
    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;
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
        return product.getName() ;
    }
}
