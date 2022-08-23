package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Address;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class ReceivedOrderDTO {

    private BigInteger id;
    private String productName;
    private Integer qty;
    private String consumerName;
    private String address;
    private String consumerPhone;

    public ReceivedOrderDTO(Object[] order) {
        this.id = (BigInteger) order[0];
        this.productName = (String) order[1];
        this.qty = (Integer) order[2];
        this.consumerName = (String) order[3];
        this.consumerPhone = (String) order [4];
        this.address = (String) order[6];
    }

    public static List<ReceivedOrderDTO> converter(List<Object[]> itens) {
        return itens.stream().map(ReceivedOrderDTO::new).collect(Collectors.toList());
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

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

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }
}
