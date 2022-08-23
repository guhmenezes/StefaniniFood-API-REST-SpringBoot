package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.dto.*;
import br.com.stefanini.stefaninifood.controller.request.OrderedItensRequest;
import br.com.stefanini.stefaninifood.model.*;
import br.com.stefanini.stefaninifood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedido")
public class OrderController {

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

    @GetMapping
    public List<CompanyDTO> retrieveCompaniesWithMenu(){
        List<Company> companies = companyRepository.findAll();
        return CompanyDTO.converter(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveMenuByIdCompany(@PathVariable Long id){
        List<Product> products = productRepository.findByCompanyId(id);
        if(products.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa não possui itens em seu cardápio.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(ProductDTO.converter(products));
    }

    @PostMapping("/item")
    public ResponseEntity<?> addItem(@RequestBody OrderedItensRequest orderedItensRequest){
        try {
            OrderedItens orderedItens = orderedItensRequest.toModel(consumerRepository, productRepository, orderRepository);
            orderedItensRepository.save(orderedItens);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OrderedItensDTO(orderedItens));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro no Body Request. Verifique ID's informados");
        }
    }

    @GetMapping("/carrinho/{id}")
    public ResponseEntity<?> cartByConsumerId(@PathVariable Long id){
        List<Object[]> itens = orderedItensRepository.findByConsumerId(id);
        if(itens.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrinho vazio ou cliente inexistente");
        }
        return ResponseEntity.status(HttpStatus.OK).body(CartDTO.converter(itens));
    }
//
//    @GetMapping("/orders/{idCompany}")
//    public List<OrderReceivedDTO> ordersReceived

    @PutMapping("/buy/{idConsumer}")
    public ResponseEntity<?> confirmOrder(@PathVariable("idConsumer") Long id){
        Optional<Consumer> consumer = consumerRepository.findById(id);
        if (consumer.isPresent()) {
            System.out.println(!(consumer.get().getAddress().toString().equals("Endereço não informado pelo usuário")));
            System.out.println(!(consumer.get().getAddress() == null));
            if (!(consumer.get().getAddress().toString().equals("Endereço não informado pelo usuário") ||
                    consumer.get().getAddress() == null)) {
                List<Order> itens = orderRepository.findByConsumerId(id);
                itens.forEach((i) -> {
                    System.out.println(i.getTotal());
                    i.setStatus(StatusOrder.EM_PREPARACAO);
                    orderRepository.save(i);
                });
                List<Object[]> purchased = orderedItensRepository.findByConsumerId(id);
                System.out.println(purchased);
                List<CartDTO> response = CartDTO.converter(purchased);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço de entrega não informado");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
    }

    @DeleteMapping("/carrinho/{idOrder}")
    @Transactional
    public ResponseEntity<?> removeProduct(@PathVariable("idOrder") Long id){
        try {
            orderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

}
