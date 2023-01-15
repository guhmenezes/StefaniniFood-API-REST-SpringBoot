package br.com.stefanini.stefaninifood.controller.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersDTO {

    private BigInteger companyId;
    private BigInteger orderId;
        private String orderStatus;
        private BigInteger consumerId;
        private Timestamp boughtAt;
        private Integer qty;
        private String productName;
        private BigInteger productId;
        private String company;

    public OrdersDTO(Object[] itens) {
        this.orderId = (BigInteger) itens[0];
        this.orderStatus = (String) itens[1];
        this.consumerId = (BigInteger) itens[2];
        this.boughtAt = (Timestamp) itens[3];
        this.qty = (Integer) itens[4];
        this.productName = (String) itens[5];
        this.productId = (BigInteger) itens[6];
        this.company = (String) itens[7];
        this.companyId = (BigInteger) itens[8];
    }

    public static List<OrdersDTO> converter(List<Object[]> itens) {
        return itens.stream().map(OrdersDTO::new).collect(Collectors.toList());
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigInteger getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(BigInteger consumerId) {
        this.consumerId = consumerId;
    }

    public Timestamp getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(Timestamp boughtAt) {
        this.boughtAt = boughtAt;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigInteger getCompanyId() {
        return companyId;
    }

    public void setCompanyId(BigInteger companyId) {
        this.companyId = companyId;
    }
}
