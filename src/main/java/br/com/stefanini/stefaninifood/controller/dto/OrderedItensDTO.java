package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.OrderedItens;

import java.util.List;
import java.util.stream.Collectors;

public class OrderedItensDTO {

    private String productName;
    private Integer qty;
    private Double priceUnit;
    private Double total;

    public OrderedItensDTO(OrderedItens orderedItens) {
        this.productName = orderedItens.getProduct().getName();
        this.qty = orderedItens.getQty();
        this.priceUnit = orderedItens.getUnitPrice();
        this.total = orderedItens.getOrder().getTotal();
    }

    public static List<OrderedItensDTO> converter(List<OrderedItens> itens) {
        return itens.stream().map(OrderedItensDTO::new).collect(Collectors.toList());
    }

//    public static List<OrderedItensDTO> converterToCart(List<Order> itens) {
//        return itens.stream().map(OrderedItensDTO::new).collect(Collectors.toList());
//    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
