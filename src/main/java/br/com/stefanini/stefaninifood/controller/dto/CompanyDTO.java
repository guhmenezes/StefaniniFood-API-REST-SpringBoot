package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.Company;
import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.model.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDTO {

    private Long id;
    private String name;
    private String cnpj;

    private List<ProductDTO> products = new ArrayList<>();

    public CompanyDTO(Company company){
        this.id = company.getId();
        this.name = company.getName();
        this.cnpj = company.getCnpj();
    }

    public static List<CompanyDTO> converter(List<Company> companies) {
        return companies.stream().map(CompanyDTO::new).collect(Collectors.toList());
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}

