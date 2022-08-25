package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Address;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConsumerAddressRequest {
    @NotNull
    @NotBlank @Length(min = 8, max = 9)
    private String zipCode;
    @NotBlank @Length(min = 6, max = 255)
    private String street;
    @DecimalMax("9999")
    private Integer number;
    @Length(max = 255)
    private String complement = "N/A";
    @NotBlank @Length(min = 6, max = 255)
    private String district;
    @NotBlank @Length(min = 3, max = 30)
    private String city;

    @NotBlank @Length(min = 2, max = 2)
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

    public Address update(Long id, ConsumerRepository consumerRepository) {
        Address address = consumerRepository.findById(id).get().getAddress();
        address.setCep(zipCode);
        address.setLogradouro(street);
        address.setNumero(number);
        address.setComplemento(complement);
        address.setBairro(district);
        address.setLocalidade(city);
        address.setUf(uf);
        return address;
    }
}
