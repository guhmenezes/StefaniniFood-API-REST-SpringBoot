package br.com.stefanini.stefaninifood.controller.request;

import br.com.stefanini.stefaninifood.model.Company;
import br.com.stefanini.stefaninifood.model.Product;
import br.com.stefanini.stefaninifood.repository.CompanyRepository;
import br.com.stefanini.stefaninifood.repository.ProductRepository;

public class ProductRequest {
    private String name;
    private Double price;
    private String description;

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

    public Product toModel(Long id, CompanyRepository companyRepository, ProductRepository productRepository){
        if(companyRepository != null) {
            Company company = companyRepository.findById(id).get();
            return new Product(name, price, description, company);
        }
        Product product = productRepository.findById(id).get();
        if(!name.isEmpty()) product.setName(name);
        if(!price.toString().isEmpty()) product.setPrice(price);
        if(!description.isEmpty()) product.setDescription(description);
        return product;
    }
}
