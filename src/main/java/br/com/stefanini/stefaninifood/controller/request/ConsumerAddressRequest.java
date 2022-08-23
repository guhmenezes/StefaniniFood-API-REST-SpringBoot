package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Address;

import javax.validation.constraints.NotBlank;

public class ConsumerAddressRequest {
    @NotBlank
    private String zipCode;
    @NotBlank
    private String street;
    @NotBlank
    private Integer number;
    private String complement;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @NotBlank
    private String uf;

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

    public void setAdress(){
        // atualiza endereço
    }
}
