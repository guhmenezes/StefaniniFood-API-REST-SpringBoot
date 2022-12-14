package br.com.stefanini.stefaninifood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
        @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
        @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
        @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
        @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
        @NamedQuery(name = "Product.findByCompanyId", query = "SELECT p FROM Product p WHERE p.company.id = :id")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(name="nome")
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private String description;
    @JsonIgnore
    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "product")
    private List<OrderedItens> products = new ArrayList<>();


    public Product() {
    }

    public Product(String name, Double price, String description, Company company) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.company = company;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
        System.out.println(this.company);
    }

    public List<OrderedItens> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedItens> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", company=" + company.getName() +
                '}';
    }
}

