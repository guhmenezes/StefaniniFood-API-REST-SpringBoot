package br.com.stefanini.stefaninifood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private Double total = 0.0;

    @JsonIgnore
    @ManyToOne
    private Consumer consumer;

    private LocalDateTime boughtAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private StatusOrder status = StatusOrder.AGUARDANDO;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderedItens> products = new ArrayList<>();

    public Order(){

    }
    public Order(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public void addItem(OrderedItens item){
        this.total += item.getTotal();
        System.out.println(total);
        this.setTime();
        item.setOrder(this);
        this.products.add(item);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public LocalDateTime getTime() {
        return boughtAt;
    }

    public void setTime() {
        this.boughtAt = LocalDateTime.now();
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public List<OrderedItens> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedItens> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "{" +
                "Pedido Nº " + id +
                ", Produto: " + products.stream().map((i)-> i.getProduct().getName()).collect(Collectors.joining()) +
                '}';
    }
}
