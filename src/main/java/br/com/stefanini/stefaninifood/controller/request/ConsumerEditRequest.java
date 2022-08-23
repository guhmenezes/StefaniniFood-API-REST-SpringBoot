package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class ConsumerEditRequest {
    @NotNull @NotEmpty @NotBlank @Length(min = 4, max = 10)
    private String password;
    @NotNull @NotEmpty @Email
    private String email;
    @NotNull @DecimalMax("99999990000") @Length(min = 11, max = 12)
    private String phone;
//    @NotBlank
//    private String zipCode;
//    @NotBlank
//    private String street;
//    @NotBlank
//    private Integer number;
//    private String complement;
//    @NotBlank
//    private String district;
//    @NotBlank
//    private String city;
//    @NotBlank
//    private String uf;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public String getZipCode() {
//        return zipCode;
//    }
//
//    public void setZipCode(String zipCode) {
//        this.zipCode = zipCode;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public Integer getNumber() {
//        return number;
//    }
//
//    public void setNumber(Integer number) {
//        this.number = number;
//    }
//
//    public String getComplement() {
//        return complement;
//    }
//
//    public void setComplement(String complement) {
//        this.complement = complement;
//    }
//
//    public String getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(String district) {
//        this.district = district;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getUf() {
//        return uf;
//    }
//
//    public void setUf(String uf) {
//        this.uf = uf;
//    }

    public Consumer update(String cpf, ConsumerRepository consumerRepository) {
        Consumer consumer = consumerRepository.findByCpf(cpf).get(0);
        consumer.setPassword(this.password);
        consumer.setEmail(this.email);
        consumer.setPhone(this.phone);
        return consumer;
    }
}
