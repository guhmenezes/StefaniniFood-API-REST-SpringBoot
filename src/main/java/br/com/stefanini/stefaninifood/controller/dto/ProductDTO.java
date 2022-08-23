package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public static List<ProductDTO> converter(List<Product> products){
        return products.stream().map(ProductDTO::new).collect(Collectors.toList());
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
}
