package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Address;
import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.model.Order;
import br.com.stefanini.stefaninifood.model.OrderedItens;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConsumerDTO {

    private Long id;
    private String name;
    private String cpf;
    private LocalDateTime createdAt;
    private List<String> address = new ArrayList<>();

    private List<CartDTO> orders = new ArrayList<>();

    public ConsumerDTO(Consumer consumer){
        this.id = consumer.getId();
        this.name = consumer.getName();
        this.cpf = consumer.getCpf();
        this.createdAt = consumer.getCreated();
        try {
            for (Address a : consumer.getAddress()) {
                this.address.add(a.toString());
            }
//            this.address = consumer.getAddress();
        } catch (Exception e){
//            this.address = Collections.singletonList("Endereço não localizado");
        }

//        this.orders = consumer.getOrder();
    }

    public static List<ConsumerDTO> converter(List<Consumer> consumers) {
        return consumers.stream().map(ConsumerDTO::new).collect(Collectors.toList());
    }

//    public

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<CartDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<CartDTO> orders) {
        this.orders = orders;
    }


//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
}
