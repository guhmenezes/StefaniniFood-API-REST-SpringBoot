package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.dto.CompletedOrderDTO;
import br.com.stefanini.stefaninifood.controller.dto.ReceivedOrderDTO;
import br.com.stefanini.stefaninifood.model.Order;
import br.com.stefanini.stefaninifood.model.StatusOrder;
import br.com.stefanini.stefaninifood.repository.ConsumerRepository;
import br.com.stefanini.stefaninifood.repository.OrderRepository;
import br.com.stefanini.stefaninifood.repository.OrderedItensRepository;
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
    OrderedItensRepository orderedItensRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ConsumerRepository consumerRepository;

    @GetMapping("/em-aberto/{idCompany}")
    public ResponseEntity<?> retrieveOrdersByCompany(@PathVariable("idCompany") Long id){
        Boolean hasOrder = !orderedItensRepository.findByCompanyId(id).isEmpty();
        System.out.println(hasOrder);
        if (hasOrder) {
            List<Object[]> inPreparationList = orderRepository.findReceivedOrdersByCompany(id);
            for (int i = 0; i < inPreparationList.size(); i++) {
                System.out.println(inPreparationList.get(i)[6].getClass().getSimpleName());
                if (inPreparationList.get(i)[6].getClass().getSimpleName() != "BigInteger") {
                    Long consumerId = Long.parseLong(inPreparationList.get(i)[6].toString());
                    String adress = consumerRepository.findById(consumerId).get().getAddress().toString();
                    inPreparationList.get(i)[6] = adress;
                }
                System.out.println(inPreparationList.get(i)[6].getClass().getSimpleName());
            }
            return ResponseEntity.status(HttpStatus.OK).body(ReceivedOrderDTO.converter(inPreparationList));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há pedidos em aberto para esta empresa");
    }

    @PutMapping("/em-aberto/{idOrder}")
    public ResponseEntity<?> completeOrder(@PathVariable("idOrder") Long id){
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            if(order.get().getStatus().equals(StatusOrder.EM_PREPARACAO)) {
                order.get().setStatus(StatusOrder.FINALIZADO);
                orderRepository.save(order.get());
                return ResponseEntity.status(HttpStatus.OK).body("Pedido finalizado com sucesso.");
           }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Pedido já finalizado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado.");
    }

    @GetMapping("/finalizados/{idCompany}")
    public ResponseEntity<?> salesListById(@PathVariable("idCompany") Long id){
        List<Object[]> soldList = orderRepository.findCompletedOrders(id);
        System.out.println(soldList);
        return ResponseEntity.status(HttpStatus.OK).body(CompletedOrderDTO.converter(soldList));
    }
}
