package br.com.stefanini.stefaninifood.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Consumer {
//    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name = "client_name")
    private String name;
    @NotNull
    @Column(unique = true)
    private String cpf;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "consumer")
    private List<Order> order;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private Boolean active = true;

    private LocalDateTime deactivedAt;

    public Consumer(){

    }
    public Consumer(String name, String cpf, String email, String phone, String password, Address address) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    public List<Address> getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        address.setConsumer(this);
//        this.address.add(address);
//    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getCreated() {
        return this.createdAt;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.deactivedAt = LocalDateTime.now();
        this.active = active;
    }

    public LocalDateTime getDeactivedAt() {
        return deactivedAt;
    }

    public void setDeactivedAt(LocalDateTime deactivedAt) {
        this.deactivedAt = deactivedAt;
    }

}
