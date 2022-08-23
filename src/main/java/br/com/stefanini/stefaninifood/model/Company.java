package br.com.stefanini.stefaninifood.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ghmenezes
 */
@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
        @NamedQuery(name = "Company.findByCNPJ", query = "SELECT c FROM Company c WHERE c.cnpj = :cnpj"),
        @NamedQuery(name = "Company.findById", query = "SELECT c FROM Company c WHERE c.id = :id"),
        @NamedQuery(name = "Company.findByName", query = "SELECT c FROM Company c WHERE c.name = :name"),
        @NamedQuery(name = "Company.findByEmail", query = "SELECT c FROM Company c WHERE c.email = :email")})
public class Company implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name="empresa")
    private String name;
    @NotNull
    private String cnpj;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String password;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "company")
    private List<Product> menu;

    public Company() {
    }

    public Company(String name, String cnpj, String email, String phone, String password) {
        this.name = name;
        this.cnpj = cnpj;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public void setMenu(List<Product> products){
        this.menu = products;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getMenu() {
        return menu;
    }

}

