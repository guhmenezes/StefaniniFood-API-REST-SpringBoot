package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.request.ConsumerAddressRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerEditRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerRequest;
import br.com.stefanini.stefaninifood.service.ConsumerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class ConsumerController {

    private ConsumerService service;

    public ConsumerController(ConsumerService service) {
        this.service = service;
    }

    @RequestMapping("/usuario")
    public ResponseEntity<?> retrieveAll(){
        ResponseEntity<?> response = service.retrieveAll();
        return response;
    }

    @GetMapping("/usuario/{cpf}")
    public ResponseEntity<?> retrieveByCpf(@PathVariable("cpf") String cpf) {
        ResponseEntity<?> response = service.retrieveByCpf(cpf);
        return response;
    }

    @GetMapping("/usuario/pedidos/{id}")
    public ResponseEntity<?> retrieveOrders(@PathVariable Long id){
        ResponseEntity<?> response = service.retrieveOrders(id);
        return response;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> createConsumer(@RequestBody @Valid ConsumerRequest consumerRequest) {
        ResponseEntity<?> response = service.createConsumer(consumerRequest);
        return response;
    }

    @PutMapping("/usuario/info/{cpf}")
    public ResponseEntity<?> updateConsumer(@PathVariable String cpf , @RequestBody @Valid ConsumerEditRequest consumerRequest){
        ResponseEntity<?> response = service.updateConsumer(cpf, consumerRequest);
        return response;
    }

    @PutMapping("/usuario/endereco/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id , @RequestBody @Valid ConsumerAddressRequest addressRequest){
        ResponseEntity<?> response = service.updateAddress(id, addressRequest);
        return response;
    }

    @PutMapping("/usuario/active/{cpf}")
    public ResponseEntity<?> activateConsumerByCpf(@PathVariable String cpf){
        ResponseEntity<?> response = service.activateConsumer(cpf);
        return response;
    }

    @DeleteMapping("/usuario/{cpf}")
    @Transactional
    public ResponseEntity<?> deleteConsumerByCpf(@PathVariable String cpf) {
        ResponseEntity<?> response = service.deleteConsumerByCpf(cpf);
        return response;
    }

    @DeleteMapping("/usuario")
    @Transactional
    public ResponseEntity<?> deactivateConsumerById(@RequestParam Long id){
        ResponseEntity<?> response = service.deactivateConsumerById(id);
        return response;
    }
}
