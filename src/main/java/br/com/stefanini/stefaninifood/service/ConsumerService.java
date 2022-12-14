package br.com.stefanini.stefaninifood.service;

import br.com.stefanini.stefaninifood.controller.dto.AddressDTO;
import br.com.stefanini.stefaninifood.controller.dto.BuyDTO;
import br.com.stefanini.stefaninifood.controller.dto.CartDTO;
import br.com.stefanini.stefaninifood.controller.dto.ConsumerDTO;
import br.com.stefanini.stefaninifood.controller.request.ConsumerAddressRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerEditRequest;
import br.com.stefanini.stefaninifood.controller.request.ConsumerRequest;
import br.com.stefanini.stefaninifood.model.Address;
import br.com.stefanini.stefaninifood.model.Consumer;
import br.com.stefanini.stefaninifood.model.OrderedItens;
import br.com.stefanini.stefaninifood.repository.AddressRepository;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import br.com.stefanini.stefaninifood.repository.OrderedItensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerService {
    @Autowired
    ConsumerRepository consumerRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderedItensRepository orderedItensRepository;

    public ResponseEntity<?> retrieveAll(){
        try {
            List<Consumer> list = consumerRepository.findAll();
            if(list.size() > 0) {
                List<ConsumerDTO> consumers = list.stream().map((c)->getAllOrders(c)).collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.OK).body(consumers);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Nenhum cliente se cadastrou ainda. Que tal ser o primeiro?");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro no servidor. Tente novamente em breve!");
        }
    }

    public ResponseEntity<?> retrieveByCpf(String cpf) {
        try {
            Consumer consumer = consumerRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("Usu??rio n??o encontrado"));
            ConsumerDTO consumerDTO = getAllOrders(consumer);
            return ResponseEntity.status(HttpStatus.OK).body(consumerDTO);
//            return ResponseEntity.status(HttpStatus.OK).body(ConsumerDTO.converter(Arrays.asList(consumer)));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");
        }
    }

    public ResponseEntity<?> createConsumer(ConsumerRequest consumerRequest) {
        try {
            Consumer consumer = consumerRequest.toModel(addressRepository);
            consumerRepository.save(consumer);
            ConsumerDTO consumerDTO = new ConsumerDTO(consumer);
            return ResponseEntity.status(HttpStatus.CREATED).body(consumerDTO);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF j?? cadastrado");
        }
    }

    public ResponseEntity<?> updateConsumer(String cpf, ConsumerEditRequest consumerRequest){
        try {
            Consumer consumer = consumerRequest.update(cpf, consumerRepository);
            consumerRepository.save(consumer);
            return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(consumerRequest));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");
        }
    }

    public ResponseEntity<?> updateAddress(Long id , ConsumerAddressRequest addressRequest){
        try {
            Address address = addressRequest.update(id, consumerRepository);
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.OK).body(AddressDTO.converter(address));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio n??o encontrado");
        }
    }

    public ResponseEntity<?> deleteConsumerByCpf(String cpf) {
        try {
            Consumer consumer = consumerRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException());
            List<Object[]> itens = orderedItensRepository.findAllByConsumerId(consumer.getId());
            if(itens.size() == 0){
                consumerRepository.delete(consumer);
                return ResponseEntity.status(HttpStatus.OK).body("Removido usu??rio com CPF " + cpf);
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("N??o foi poss??vel remover usu??rio com CPF " + cpf + " por existirem pedidos vinculados ?? este cliente. \n" + consumer.getOrder().toString());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio com CPF informado n??o encontrado");
        }
    }

    public ResponseEntity<?> deactivateConsumerById(Long id){
        try {
            Consumer consumer = consumerRepository.findById(id).orElseThrow(() -> new RuntimeException());
            consumer.setActive(false);
            return ResponseEntity.status(HttpStatus.OK).body("Conta desativada usu??rio com ID " + id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio com ID informado n??o encontrado");
        }
    }

    public ResponseEntity<?> activateConsumer(String cpf) {
        try {
            Consumer consumer = consumerRepository.findDeactivatedByCpf(cpf).orElseThrow(() -> new RuntimeException());
            consumer.setActive(true);
            consumer.setDeactivedAt(null);
            consumerRepository.save(consumer);
            ConsumerDTO consumerDTO = getAllOrders(consumer);
            return ResponseEntity.status(HttpStatus.OK).body(consumerDTO);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usu??rio j?? ativo ou inexistete");
        }
    }

    public ConsumerDTO getAllOrders(Consumer consumer){
        List<Object[]> itens = orderedItensRepository.findAllByConsumerId(consumer.getId());
        List<CartDTO> cart = CartDTO.converter(itens);
        ConsumerDTO consumerDTO = new ConsumerDTO(consumer);
        consumerDTO.setOrders(cart);
        return consumerDTO;
    }

    public ResponseEntity<?> retrieveOrders(Long id) {
        List<OrderedItens> bought = orderedItensRepository.findBoughtByConsumerId(id);
        List<BuyDTO> buyDTO = BuyDTO.converter(bought);
        return ResponseEntity.status(HttpStatus.OK).body(buyDTO);
    }
}
