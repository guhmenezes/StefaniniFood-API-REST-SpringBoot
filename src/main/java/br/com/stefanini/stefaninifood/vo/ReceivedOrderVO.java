package br.com.stefanini.stefaninifood.vo;

import br.com.stefanini.stefaninifood.model.Address;

public class ReceivedOrderVO {

    private String productName;
    private Integer qty;
    private String consumerName;
    private String address;
    private String consumerPhone;

    public ReceivedOrderVO(String productName, Integer qty, String consumerName, Address address, String consumerPhone) {
        this.productName = productName;
        this.qty = qty;
        this.consumerName = consumerName;
        this.address = address.toString();
        this.consumerPhone = consumerPhone;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQty() {
        return qty;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getAddress() {
        return address;
    }

    public String getConsumerPhone() {
        return consumerPhone;
    }
}
