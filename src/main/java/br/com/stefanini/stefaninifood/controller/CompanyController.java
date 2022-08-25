package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.dto.CompletedOrderDTO;
import br.com.stefanini.stefaninifood.controller.dto.ReceivedOrderDTO;
import br.com.stefanini.stefaninifood.model.Order;
import br.com.stefanini.stefaninifood.model.StatusOrder;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import br.com.stefanini.stefaninifood.repository.OrderRepository;
import br.com.stefanini.stefaninifood.repository.OrderedItensRepository;
import br.com.stefanini.stefaninifood.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
}
