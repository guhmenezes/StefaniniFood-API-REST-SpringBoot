package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.dto.*;
import br.com.stefanini.stefaninifood.controller.request.OrderedItensRequest;
import br.com.stefanini.stefaninifood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping
    public List<CompanyDTO> retrieveCompaniesWithMenu(){
        List<CompanyDTO> companies = service.retrieveCompaniesWithMenu();
        return companies;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveMenuByIdCompany(@PathVariable Long id){
        ResponseEntity<?> response = service.retrieveMenuByIdCompany(id);
        return response;
    }

    @PostMapping("/item")
    public ResponseEntity<?> addItem(@RequestBody OrderedItensRequest orderedItensRequest){
        ResponseEntity<?> response = service.addItem(orderedItensRequest);
        return response;
    }

    @GetMapping("/carrinho/{id}")
    public ResponseEntity<?> cartByConsumerId(@PathVariable Long id){
        ResponseEntity<?> response = service.cartByConsumerId(id);
        return response;
    }

    @PutMapping("/buy/{idConsumer}")
    public ResponseEntity<?> confirmOrder(@PathVariable("idConsumer") Long id){
        ResponseEntity<?> response = service.confirmOrder(id);
        return response;
    }

    @DeleteMapping("/carrinho/{idOrder}")
    @Transactional
    public ResponseEntity<?> removeProduct(@PathVariable("idOrder") Long id){
        ResponseEntity<?> response = service.removeProduct(id);
        return response;
    }

}
