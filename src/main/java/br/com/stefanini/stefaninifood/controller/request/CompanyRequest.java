package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Company;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.*;

public class CompanyRequest {

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 30)
    private String name;

    @NotNull @NotEmpty @CNPJ
    private String cnpj;

    @NotNull @NotEmpty @NotBlank
    @Length(min = 4, max = 10)
    private String password;

    @NotNull @DecimalMax("99999990000") @Length(min = 11, max = 12)
    private String phone;

    @NotNull @NotEmpty @Email
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company toModel() {
        return new Company(name, cnpj, email, phone, password);
    }
}
