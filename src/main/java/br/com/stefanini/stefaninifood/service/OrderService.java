package br.com.stefanini.stefaninifood.service;

import br.com.stefanini.stefaninifood.controller.dto.CartDTO;
import br.com.stefanini.stefaninifood.controller.dto.CompanyDTO;
import br.com.stefanini.stefaninifood.controller.dto.OrderedItensDTO;
import br.com.stefanini.stefaninifood.controller.dto.ProductDTO;
import br.com.stefanini.stefaninifood.controller.request.OrderedItensRequest;
import br.com.stefanini.stefaninifood.model.*;
import br.com.stefanini.stefaninifood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedItensRepository orderedItensRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<CompanyDTO> retrieveCompaniesWithMenu(){
        List<Company> companies = companyRepository.findAll();
        return CompanyDTO.converter(companies);
    }

    public ResponseEntity<?> retrieveMenuByIdCompany(Long id){
        List<Product> products = productRepository.findByCompanyId(id);
        if(products.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não possui itens em seu cardápio.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(ProductDTO.converter(products));
    }

    public ResponseEntity<?> addItem(OrderedItensRequest orderedItensRequest){
        try {
            OrderedItens orderedItens = orderedItensRequest.toModel(consumerRepository, productRepository, orderRepository);
            orderedItensRepository.save(orderedItens);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OrderedItensDTO(orderedItens));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro no Body Request. Verifique ID's informados");
        }
    }

    public ResponseEntity<?> cartByConsumerId(Long id){
        List<Object[]> itens = orderedItensRepository.findByConsumerId(id);
        if(itens.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho vazio ou cliente inexistente");
        }
        return ResponseEntity.status(HttpStatus.OK).body(CartDTO.converter(itens));
    }

    public ResponseEntity<?> confirmOrder(Long id){
        Optional<Consumer> consumer = consumerRepository.findById(id);
        if (consumer.isPresent()) {
            if (!(consumer.get().getAddress().toString().equals("Endereço não informado pelo usuário") ||
                    consumer.get().getAddress() == null)) {
                List<Order> itens = orderRepository.findByConsumerId(id);
                itens.forEach((i) -> {
                    System.out.println(i.getTotal());
                    i.setStatus(StatusOrder.EM_PREPARACAO);
                    orderRepository.save(i);
                });
//                List<Object[]> purchased = orderedItensRepository.findByConsumerId(id);
//                List<CartDTO> response = CartDTO.converter(purchased);
                return ResponseEntity.status(HttpStatus.OK).body("Pedido realizado com sucesso");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço de entrega não informado");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
    }

    public ResponseEntity<?> removeProduct(Long id){
        try {
            orderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

}
