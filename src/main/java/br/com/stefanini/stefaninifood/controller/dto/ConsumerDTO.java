package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.model.OrderedItens;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsumerDTO {

    private Long id;
    private String name;
    private String cpf;
    private LocalDateTime createdAt;
    private String addres;

    private List<CartDTO> orders = new ArrayList<>();

    public ConsumerDTO(Consumer consumer){
        this.id = consumer.getId();
        this.name = consumer.getName();
        this.cpf = consumer.getCpf();
        this.createdAt = consumer.getCreated();
        try {
            this.addres = consumer.getAddress().toString();
        } catch (Exception e){
            this.addres = "Endereço não localizado";
        }
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

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public List<CartDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<CartDTO> orders) {
        this.orders = orders;
    }
}
