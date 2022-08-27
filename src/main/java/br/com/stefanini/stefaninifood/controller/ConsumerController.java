package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.request.ConsumerAddressRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerEditRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerRequest;
import br.com.stefanini.stefaninifood.service.ConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@Api(value = "Endpoint Clientes")
public class ConsumerController {

    private ConsumerService service;

    public ConsumerController(ConsumerService service) {
        this.service = service;
    }

    @GetMapping("/usuario")
    @ApiOperation(value = "Retorna uma lista com todos os clientes ativos e seus respectivos pedidos.")
    public ResponseEntity<?> retrieveAll(){
        ResponseEntity<?> response = service.retrieveAll();
        return response;
    }

    @GetMapping("/usuario/{cpf}")
    @ApiOperation(value = "Retorna um cliente ativo e seus respectivos pedidos pelo CPF.")
    public ResponseEntity<?> retrieveByCpf(@PathVariable("cpf") String cpf) {
        ResponseEntity<?> response = service.retrieveByCpf(cpf);
        return response;
    }

    @GetMapping("/usuario/pedidos/{id}")
    @ApiOperation(value = "Retorna uma lista com os pedidos do cliente ativo pelo ID.")
    public ResponseEntity<?> retrieveOrders(@PathVariable Long id){
        ResponseEntity<?> response = service.retrieveOrders(id);
        return response;
    }

    @PostMapping("/cadastro")
    @ApiOperation(value = "Cadastra um novo cliente.")
    public ResponseEntity<?> createConsumer(@RequestBody @Valid ConsumerRequest consumerRequest) {
        ResponseEntity<?> response = service.createConsumer(consumerRequest);
        return response;
    }

    @PutMapping("/usuario/info/{cpf}")
    @ApiOperation(value = "Atualiza os dados pessoais do cliente pelo CPF.")
    public ResponseEntity<?> updateConsumer(@PathVariable String cpf , @RequestBody @Valid ConsumerEditRequest consumerRequest){
        ResponseEntity<?> response = service.updateConsumer(cpf, consumerRequest);
        return response;
    }

    @PutMapping("/usuario/endereco/{id}")
    @ApiOperation(value = "Atualiza o endereço do cliente pelo ID.")
    public ResponseEntity<?> updateAddress(@PathVariable Long id , @RequestBody @Valid ConsumerAddressRequest addressRequest){
        ResponseEntity<?> response = service.updateAddress(id, addressRequest);
        return response;
    }

    @PutMapping("/usuario/active/{cpf}")
    @ApiOperation(value = "Ativa o cadastro do cliente inativo pelo CPF.")
    public ResponseEntity<?> activateConsumerByCpf(@PathVariable String cpf){
        ResponseEntity<?> response = service.activateConsumer(cpf);
        return response;
    }

    @DeleteMapping("/usuario/{cpf}")
    @Transactional
    @ApiOperation(value = "Apaga os registros de um cliente cadastrado pelo CPF.")
    public ResponseEntity<?> deleteConsumerByCpf(@PathVariable String cpf) {
        ResponseEntity<?> response = service.deleteConsumerByCpf(cpf);
        return response;
    }

    @DeleteMapping("/usuario")
    @Transactional
    @ApiOperation(value = "Desativa o cadastro do cliente ativo pelo ID.")
    public ResponseEntity<?> deactivateConsumerById(@RequestParam Long id){
        ResponseEntity<?> response = service.deactivateConsumerById(id);
        return response;
    }
}
