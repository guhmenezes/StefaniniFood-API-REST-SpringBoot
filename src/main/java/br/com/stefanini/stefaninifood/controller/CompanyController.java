package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.request.CompanyRequest;
import br.com.stefanini.stefaninifood.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class CompanyController {

    @Autowired
    CompanyService service;


    @GetMapping("/em-aberto/{idCompany}")
    public ResponseEntity<?> retrieveOrdersByCompany(@PathVariable("idCompany") Long id){
        ResponseEntity<?> response = service.retrieveOrdersByCompany(id);
        return response;
    }

    @PutMapping("/em-aberto/{idOrder}")
    public ResponseEntity<?> completeOrder(@PathVariable("idOrder") Long id){
        ResponseEntity<?> response = service.completeOrder(id);
        return response;
    }

    @GetMapping("/finalizados/{idCompany}")
    public ResponseEntity<?> salesListById(@PathVariable("idCompany") Long id){
        ResponseEntity<?> response = service.salesListById(id);
        return response;
    }

    @PostMapping("/cadastro-empresa")
    public ResponseEntity<?> createCompany(@RequestBody @Valid CompanyRequest companyRequest){
        ResponseEntity<?> response = service.createCompany(companyRequest);
        return response;
    }

    @DeleteMapping("/empresa/{id}")
    @Transactional
    public ResponseEntity<?> deleteConsumerById(@PathVariable Long id) {
        ResponseEntity<?> response = service.deleteCompanyById(id);
        return response;
    }

}
