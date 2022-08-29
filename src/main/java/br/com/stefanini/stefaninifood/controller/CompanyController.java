package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.request.CompanyRequest;
import br.com.stefanini.stefaninifood.controller.request.ProductRequest;
import br.com.stefanini.stefaninifood.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@Api(value = "Endpoint Empresas")
public class CompanyController {

    @Autowired
    CompanyService service;


    @GetMapping("/em-aberto/{idCompany}")
    @Cacheable(value = "openedOrders")
    @ApiOperation(value = "Retorna uma lista com todos os pedidos em aberto da empresa pelo ID.")
    public ResponseEntity<?> retrieveOrdersByCompany(@PathVariable("idCompany") Long id){
        ResponseEntity<?> response = service.retrieveOrdersByCompany(id);
        return response;
    }

    @PutMapping("/em-aberto/{idOrder}")
    @ApiOperation(value = "Finaliza um pedido em aberto por seu ID.")
    public ResponseEntity<?> completeOrder(@PathVariable("idOrder") Long id){
        ResponseEntity<?> response = service.completeOrder(id);
        return response;
    }

    @GetMapping("/finalizados/{idCompany}")
    @ApiOperation(value = "Retorna uma lista com todos os pedidos já finalizados pelo ID.")
    public ResponseEntity<?> salesListById(@PathVariable("idCompany") Long id){
        ResponseEntity<?> response = service.salesListById(id);
        return response;
    }

    @PostMapping("/empresa")
    @ApiOperation(value = "Cadastra uma nova empresa.")
    public ResponseEntity<?> createCompany(@RequestBody @Valid CompanyRequest companyRequest){
        ResponseEntity<?> response = service.createCompany(companyRequest);
        return response;
    }

    @DeleteMapping("/empresa/{id}")
    @Transactional
    @ApiOperation(value = "Apaga os registros de uma empresa cadastrada pelo ID.")
    public ResponseEntity<?> deleteConsumerById(@PathVariable Long id) {
        ResponseEntity<?> response = service.deleteCompanyById(id);
        return response;
    }

    @PostMapping("/cardapio/{idCompany}")
    @Transactional
    @ApiOperation(value = "Adiciona um novo produto no cardápio pelo ID da empresa.")
    public ResponseEntity<?> addProduct(@PathVariable("idCompany") Long id, @RequestBody ProductRequest productRequest){
        ResponseEntity<?> response = service.productHandler(id, productRequest, false);
        return response;
    }

    @PutMapping("/cardapio/produto/{idProduct}")
    @ApiOperation(value = "Atualiza um produto no cardápio pelo ID")
    public ResponseEntity<?> updateProduct(@PathVariable("idProduct") Long id, ProductRequest productRequest){
        ResponseEntity<?> response = service.productHandler(id, productRequest, true);
        return response;
    }

    @DeleteMapping("/cardapio/{idProduct}")
    @ApiOperation(value = "Remove um produto do cardápio pelo ID")
    public ResponseEntity<?> deleteProduct(@PathVariable("idProduct") Long id){
        ResponseEntity<?> response = service.deleteProduct(id);
        return response;
    }
}
