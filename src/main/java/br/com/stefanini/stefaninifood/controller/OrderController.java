package br.com.stefanini.stefaninifood.controller;

import br.com.stefanini.stefaninifood.controller.dto.*;
import br.com.stefanini.stefaninifood.controller.request.OrderedItensRequest;
import br.com.stefanini.stefaninifood.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Api(value = "Endpoint Pedidos")
@RequestMapping("/pedido")
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com todas as empresas com cardápio.")
    public List<CompanyDTO> retrieveCompaniesWithMenu(){
        List<CompanyDTO> companies = service.retrieveCompaniesWithMenu();
        return companies;
    }

    @GetMapping("/{id}")
//    @Cacheable(value = "menu")
    @ApiOperation(value = "Retorna o cardápio de uma empresa pelo ID")
    public ResponseEntity<?> retrieveMenuByIdCompany(@PathVariable Long id){
        ResponseEntity<?> response = service.retrieveMenuByIdCompany(id);
        return response;
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> retrieveProduct(@PathVariable Long id){
        ResponseEntity<?> response = service.retrieveProduct(id);
        return response;
    }

    @Transactional
    @PutMapping("/item/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestParam Integer qty){
        ResponseEntity<?> response = service.updateProduct(id, qty);
        return response;
    }

    @PostMapping("/item")
    @Transactional
    @ApiOperation(value = "Cria/Adiciona um produto no carrinho do cliente pelo ID do cliente e ID do produto.")
    public ResponseEntity<?> addItem(@RequestBody OrderedItensRequest orderedItensRequest){
        ResponseEntity<?> response = service.addItem(orderedItensRequest);
        return response;
    }

    @Transactional
    @PostMapping("/itens")
    @ApiOperation(value = "Cria/Adiciona uma lista de produtos no carrinho do cliente pelo ID do cliente e ID do produto.")
    public ResponseEntity<?> addItens(@RequestBody List<OrderedItensRequest> orderedItensRequest){
        ResponseEntity<?> response = service.addItens(orderedItensRequest);
        return response;
    }

    @GetMapping("/carrinho/{id}")
    @ApiOperation(value = "Retorna o carrinho do cliente pelo ID.")
    public ResponseEntity<?> cartByConsumerId(@PathVariable Long id){
        ResponseEntity<?> response = service.cartByConsumerId(id);
        return response;
    }

    @PutMapping("/buy/{idConsumer}")
    @CacheEvict(value = "openedOrders", allEntries = true)
    @ApiOperation(value = "Efetua a compra de todos os produtos no carrinho do cliente pelo ID.")
    public ResponseEntity<?> confirmOrder(@PathVariable("idConsumer") Long id){
        ResponseEntity<?> response = service.confirmOrder(id);
        return response;
    }

    @DeleteMapping("/carrinho/{idOrder}")
    @Transactional
    @ApiOperation(value = "Remove um produto do carrinho do cliente pelo ID do pedido.")
    public ResponseEntity<?> removeProduct(@PathVariable("idOrder") Long id){
        ResponseEntity<?> response = service.removeProduct(id);
        return response;
    }

    @DeleteMapping("/order/{consumerId}")
    @Transactional
    @ApiOperation(value = "Remove todos os produtos do carrinho do cliente pelo ID do pedido.")
    public ResponseEntity<?> removeAllProducts(@PathVariable("consumerId") Long id){
        ResponseEntity<?> response = service.clearCart(id);
        return response;
    }

}
