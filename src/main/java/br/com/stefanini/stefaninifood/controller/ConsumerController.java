package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.dto.ConsumerDTO;
import br.com.stefanini.stefaninifood.controller.request.ConsumerEditRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerRequest;
import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.repository.AddressRepository;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping("/usuario")
    public List<ConsumerDTO> retrieveAll(){
        List<Consumer> list = consumerRepository.findAll();
        return ConsumerDTO.converter(list);
    }

    @GetMapping("/usuario/{cpf}")
    public ResponseEntity<?> retrieveByCpf(@PathVariable("cpf") String cpf) {
        try {
            List<Consumer> list = consumerRepository.findByCpf(cpf);
            return ResponseEntity.status(HttpStatus.OK).body(ConsumerDTO.converter(list));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<ConsumerDTO> createConsumer(@RequestBody @Valid ConsumerRequest consumerRequest) {
        Consumer consumer = consumerRequest.toModel(addressRepository);
        consumerRepository.save(consumer);
        ConsumerDTO consumerDTO = new ConsumerDTO(consumer);
        return ResponseEntity.status(HttpStatus.CREATED).body(consumerDTO);
    }

    @PutMapping("/usuario/{cpf}")
    public ResponseEntity<?> updateConsumer(@PathVariable String cpf , @RequestBody @Valid ConsumerEditRequest consumerRequest){
        try {
            Consumer consumer = consumerRequest.update(cpf, consumerRepository);
            consumerRepository.save(consumer);
            return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(consumerRequest));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}
