package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.config.validation.NullAddressException;
import br.com.stefanini.stefaninifood.model.Address;
import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.repository.AddressRepository;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.*;

public class ConsumerRequest {

    @NotNull @NotEmpty @Length(min = 3, max = 30)
    private String name;
    @NotNull @NotEmpty @CPF
    private String cpf;
    @NotNull @NotEmpty @NotBlank @Length(min = 4, max = 10)
    private String password;
    @NotNull @NotEmpty @Email
    private String email;
    @NotNull @DecimalMax("99999990000") @Length(min = 11, max = 12)
    private String phone;
    @DecimalMax("9999")
    private Integer number;
    @Length(min = 8, max = 9)
    private String zipCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Consumer toModel(AddressRepository addressRepository){
//        RestTemplate rt = new RestTemplate();
//        Address address;
//        try {
//            address = rt.getForObject("https://viacep.com.br/ws/" + zipCode.replaceAll("[^a-zA-Z0-9]", "") + "/json", Address.class);
//            address.setNumero(number);
//        } catch (Exception e) {
//            address = new Address();
//        }
//        addressRepository.save(address);
        this.password = new BCryptPasswordEncoder().encode(password);
        return new Consumer(name,cpf,email,phone,password);

    }
}
