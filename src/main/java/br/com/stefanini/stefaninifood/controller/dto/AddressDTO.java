package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Address;

public class AddressDTO {

    private String name;
    private String zipCode;
    private String street;
    private Integer number;
    private String complement;
    private String district;
    private String city;
    private String uf;

    public AddressDTO(Address address) {
        this.name = address.getConsumer().getName();
        this.zipCode = address.getCep();
        this.street = address.getLogradouro();
        this.number = address.getNumero();
        this.complement = address.getComplemento();
        this.district = address.getBairro();
        this.city = address.getLocalidade();
        this.uf = address.getUf();
    }

    public static AddressDTO converter(Address address) {
        return new AddressDTO(address);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
