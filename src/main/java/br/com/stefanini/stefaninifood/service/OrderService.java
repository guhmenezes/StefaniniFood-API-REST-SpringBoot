package br.com.stefanini.stefaninifood.service;

import br.com.stefanini.stefaninifood.controller.dto.*;
import br.com.stefanini.stefaninifood.controller.request.OrderedItensRequest;
import br.com.stefanini.stefaninifood.model.*;
import br.com.stefanini.stefaninifood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    ConsumerRepository consumerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderedItensRepository orderedItensRepository;

    public List<CompanyDTO> retrieveCompaniesWithMenu(){
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(this::getAllProducts).collect(Collectors.toList());
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
        List<Object[]> itens = orderedItensRepository.findCartByConsumerId(id);
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
                List<OrderedItens> bought = orderedItensRepository.findBoughtByConsumerId(id);
                List<BuyDTO> buyDTO = BuyDTO.converter(bought);
                buyDTO = buyDTO.stream().peek((o) -> {
                    System.out.println((o.getProductId()).getClass().getSimpleName());
                Company company = companyRepository.findCompanyByProduct(o.getProductId());
                Long companyId = company.getId();
                List<Object[]> companyDemand = companyRepository.findOrdersByCompanyId(companyId);
                o.setEstimatedTime(companyDemand.size() * 3);
                }).collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.OK).body(buyDTO);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endereço de entrega não informado");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
    }

    public ResponseEntity<?> removeProduct(Long id){
        try {
//            Order order = orderRepository.findById(id).get();
//            Consumer consumer = consumerRepository.findConsumerByOrderId(BigInteger.valueOf(id));
//            order.setConsumer(null);
            orderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    public CompanyDTO getAllProducts(Company company){
        List<Product> itens = productRepository.findByCompanyId(company.getId());
        List<ProductDTO> products = ProductDTO.converter(itens);
        CompanyDTO companyDTO = new CompanyDTO(company);
        companyDTO.setProducts(products);
        return companyDTO;
    }

}
