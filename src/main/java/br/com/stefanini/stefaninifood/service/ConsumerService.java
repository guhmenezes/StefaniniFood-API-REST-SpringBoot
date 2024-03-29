package br.com.stefanini.stefaninifood.service;

import br.com.stefanini.stefaninifood.controller.dto.*;
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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
                return ResponseEntity.status(HttpStatus.OK).body(list);
//            if(list.size() > 0) {
//                List<ConsumerDTO> consumers = list.stream().map((c)->getAllOrders(c)).collect(Collectors.toList());
//            }
//            return ResponseEntity.status(HttpStatus.OK).body("Nenhum cliente se cadastrou ainda. Que tal ser o primeiro?");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro no servidor. Tente novamente em breve!");
        }
    }

    public ResponseEntity<?> retrieveByLogin(String login) {
//        try {
            Consumer consumer = consumerRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
//            ConsumerDTO consumerDTO = getAllOrders(consumer);
//            return ResponseEntity.status(HttpStatus.OK).body(consumerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ConsumerDTO(consumer));
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
//        }
    }

    public ResponseEntity<?> createConsumer(ConsumerRequest consumerRequest) {
//        try {
            Consumer consumer = consumerRequest.toModel(addressRepository);
            consumerRepository.save(consumer);
            ConsumerDTO consumerDTO = new ConsumerDTO(consumer);
            return ResponseEntity.status(HttpStatus.CREATED).body(consumerDTO);
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado.");
//        }
    }

    public ResponseEntity<?> updateConsumer(String cpf, ConsumerEditRequest consumerRequest){
        try {
            Consumer consumer = consumerRequest.update(cpf, consumerRepository);
            consumerRepository.save(consumer);
            return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(consumerRequest));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }

    public ResponseEntity<?> updateAddress(Long id , ConsumerAddressRequest addressRequest){
//        try {
            Address address = addressRequest.update(id, addressRepository);
            addressRepository.save(address);
            return ResponseEntity.status(HttpStatus.OK).body(AddressDTO.converter(address));
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
//        }
    }

    public ResponseEntity<?> deleteConsumerByCpf(String cpf) {
        try {
            Consumer consumer = consumerRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException());
            List<Object[]> itens = orderedItensRepository.findAllByConsumerId(consumer.getId());
            if(itens.size() == 0){
                consumerRepository.delete(consumer);
                return ResponseEntity.status(HttpStatus.OK).body("Removido usuário com CPF " + cpf);
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Não foi possível remover usuário com CPF " + cpf + " por existirem pedidos vinculados à este cliente. \n" + consumer.getOrder().toString());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com CPF informado não encontrado");
        }
    }

    public ResponseEntity<?> deactivateConsumerById(Long id){
        try {
            Consumer consumer = consumerRepository.findById(id).orElseThrow(() -> new RuntimeException());
            consumer.setActive(false);
            return ResponseEntity.status(HttpStatus.OK).body("Conta desativada usuário com ID " + id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com ID informado não encontrado");
        }
    }

    public ResponseEntity<?> activateConsumer(String cpf) {
        try {
            Consumer consumer = consumerRepository.findDeactivatedByCpf(cpf).orElseThrow(() -> new RuntimeException());
            consumer.setActive(true);
            consumer.setDeactivedAt(null);
            consumerRepository.save(consumer);
//            ConsumerDTO consumerDTO = getAllOrders(consumer);
            return ResponseEntity.status(HttpStatus.OK).body(consumer);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário já ativo ou inexistete");
        }
    }

    public ResponseEntity<?> getAllOrders(Long id){
        List<Object[]> itens = orderedItensRepository.findAllOrdersByConsumerId(id);
        List<OrdersDTO> orders = OrdersDTO.converter(itens);
//        ConsumerDTO consumerDTO = new ConsumerDTO(consumer);
//        consumerDTO.setOrders(cart);
        Map<BigInteger, List<OrdersDTO>> groupById = orders.stream().collect(Collectors.groupingBy(OrdersDTO::getOrderId, Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(groupById);
    }

    public ResponseEntity<?> retrieveOrders(Long id) {
        List<OrderedItens> bought = orderedItensRepository.findBoughtByConsumerId(id);
        List<BuyDTO> buyDTO = BuyDTO.converter(bought);
        return ResponseEntity.status(HttpStatus.OK).body(buyDTO);
    }

    public ResponseEntity<?> getItem(Long id) {
        OrderedItens item = orderedItensRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    public ResponseEntity<?> addAddress(ConsumerAddressRequest addressRequest) {
//        try {
        System.out.println(addressRequest);
            Integer hasRegistry = addressRepository.hasRegistry(addressRequest.getIdConsumer(), addressRequest.getZipCode(), addressRequest.getNumber());
            if (hasRegistry == 0) {
                Address address = addressRequest.toModel(consumerRepository, addressRepository);
                addressRepository.save(address);
                return ResponseEntity.status(HttpStatus.OK).body(AddressDTO.converter(address));
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(addressRepository.getId(addressRequest.getIdConsumer(), addressRequest.getZipCode(), addressRequest.getNumber()));
            }
//        } catch (Exception e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
//        }
    }

    public ResponseEntity<?> hasRegistry(String email) {
        Integer hasRegistry = consumerRepository.hasRegistry(email);
        if (hasRegistry > 0) return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
