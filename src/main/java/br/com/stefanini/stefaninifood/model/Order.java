package br.com.stefanini.stefaninifood.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private Double total = 0.0;
    @ManyToOne
    private Consumer consumer;
    private LocalDateTime createAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private StatusOrder status = StatusOrder.AGUARDANDO;
//    private Company company;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }
}
