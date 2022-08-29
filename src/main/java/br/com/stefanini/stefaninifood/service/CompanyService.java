package br.com.stefanini.stefaninifood.service;

import br.com.stefanini.stefaninifood.controller.dto.*;
import br.com.stefanini.stefaninifood.controller.request.CompanyRequest;
import br.com.stefanini.stefaninifood.controller.request.ProductRequest;
import br.com.stefanini.stefaninifood.model.*;
import br.com.stefanini.stefaninifood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    OrderedItensRepository orderedItensRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ConsumerRepository consumerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CompanyRepository companyRepository;

    public ResponseEntity<?> retrieveOrdersByCompany(Long id) {
        try {
        List<OrderedItens> orders = orderedItensRepository.findByCompanyId(id);
        if (orders.size() > 0) {
            List<Object[]> receivedOrders = companyRepository.findReceivedOrdersByCompany(id);
            List<ReceivedOrderDTO> inPreparationList = ReceivedOrderDTO.converter(receivedOrders);
            for (int i = 0; i < inPreparationList.size(); i++) {

                // MOSTRAR ORDENS SEPARADAS
                BigInteger orderId = (BigInteger) receivedOrders.get(i)[0];
                Consumer consumer = consumerRepository.findConsumerByOrderId(orderId);
                inPreparationList.get(i).setConsumer(consumer);

//                //MOSTRAR ORDENS POR CLIENTE
//                List<Object[]> objects = companyRepository.groupOrder(consumer.getId(), id);
//                System.out.println(objects.get(0)[0].toString());

            }
            return ResponseEntity.status(HttpStatus.OK).body(inPreparationList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há pedidos em aberto para esta empresa");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro ao carregar pedidos " + e.getMessage());
        }
    }

    public ResponseEntity<?> completeOrder(Long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                if (order.get().getStatus().equals(StatusOrder.EM_PREPARACAO)) {
                    order.get().setStatus(StatusOrder.FINALIZADO);
                    orderRepository.save(order.get());
                    return ResponseEntity.status(HttpStatus.OK).body("Pedido finalizado com sucesso.");
                }
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Pedido já finalizado.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro ao carregar pedidos.");
        }
    }

    public ResponseEntity<?> salesListById(Long id) {
        try {
            List<Object[]> orders = orderRepository.findCompletedOrders(id);
            if (orders.size() > 0) {
                List<CompletedOrderDTO> soldList = CompletedOrderDTO.converter(orders);
                try {
                    soldList = soldList.stream().peek((i) -> {
                        BigInteger orderId = i.getId();
                        Consumer consumer = consumerRepository.findConsumerByOrderId(orderId);
                        i.setCliente(consumer);
                    }).collect(Collectors.toList());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.OK).body(soldList);
                }
                return ResponseEntity.status(HttpStatus.OK).body(soldList);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum pedido finalizado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Erro ao carregar pedidos.");
        }
    }

    public ResponseEntity<?> createCompany(CompanyRequest companyRequest) {
        Company company = companyRequest.toModel();
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CompanyDTO((company)));
    }

//    private CompanyDTO getProducts(Company company) {
//        List<Product> itens = companyRepository.findProductsById(company.getId());
//        List<ProductDTO> products = ProductDTO.converter(itens);
//        CompanyDTO companyDTO = new CompanyDTO(company);
//        companyDTO.setProducts(products);
//        return companyDTO;
//    }

    public ResponseEntity<?> deleteCompanyById(Long id) {
        try {
            Company company = companyRepository.findById(id).orElseThrow(RuntimeException::new);
            List<OrderedItens> itens = orderedItensRepository.findOrdersByCompanyId(company.getId());
            if (itens.size() == 0) {
                companyRepository.delete(company);
                return ResponseEntity.status(HttpStatus.OK).body("Removida empresa com ID " + id);
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Não foi possível remover a empresa " + id + " " + company.getName() + " por existirem pedidos vinculados a ela.\n" + itens);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com CPF informado não encontrado");
        }
    }

    public ResponseEntity<?> productHandler(Long id, ProductRequest productRequest, Boolean alreadyExists) {
        try {
            Product product;
            if(!alreadyExists) {
                product = productRequest.toModel(id, companyRepository, null);
                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(product));
            } else {
                product = productRequest.toModel(id, null, productRepository);
                productRepository.save(product);
                return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(product));
            }
        } catch (Exception e){
            if(alreadyExists) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não encontrada");
        }
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()) {
                productRepository.delete(product.get());
                return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso produto com ID " + id);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado produto com ID " + id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover produto.");
        }
    }
}
