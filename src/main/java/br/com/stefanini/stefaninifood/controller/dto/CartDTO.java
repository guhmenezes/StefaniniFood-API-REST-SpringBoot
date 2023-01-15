package br.com.stefanini.stefaninifood.controller.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {

    private BigInteger itemId;
    private BigInteger productId;
    private BigInteger companyId;
    private BigInteger id;
    private String nome;
    private Double unit_price;
    private Integer qty;
    private Double total;
    private String status;

    private String company;
    public CartDTO(Object[] itens) {
        this.id = (BigInteger) itens[0];
        this.nome = (String) itens[1];
        this.unit_price = (Double) itens[2];
        this.qty = (Integer) itens[3];
        this.total = qty * unit_price;
        this.status = (String) itens[5];
        this.company = (String) itens[6];
        this.companyId = (BigInteger) itens[7];
        this.productId = (BigInteger) itens[8];
        this.itemId = (BigInteger) itens[9];
    }

    public static List<CartDTO> converter(List<Object[]> itens) {
        return itens.stream().map(CartDTO::new).collect(Collectors.toList());
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigInteger getItemId() {
        return itemId;
    }

    public BigInteger getCompanyId() {
        return companyId;
    }

    public void setCompanyId(BigInteger companyId) {
        this.companyId = companyId;
    }

    public void setItemId(BigInteger itemId) {
        this.itemId = itemId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }
}
