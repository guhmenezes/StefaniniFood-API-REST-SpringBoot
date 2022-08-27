package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Consumer;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class CompletedOrderDTO {
    private BigInteger id;
    private String produto;
    private Double total;

    private String cliente = "Cliente não encontrado";

    public CompletedOrderDTO(Object[] sales) {
        this.id = (BigInteger) sales[0];
        this.produto = (String) sales[1];
        this.total = (Double) sales[2];
    }

    public static List<CompletedOrderDTO> converter(List<Object[]> itens){
        return itens.stream().map(CompletedOrderDTO::new).collect(Collectors.toList());
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getTotal() {
        return String.format("%.2f", total);
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(Consumer consumer) {
        this.cliente = consumer.getName();
    }
}


