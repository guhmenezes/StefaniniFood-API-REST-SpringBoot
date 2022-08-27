package br.com.stefanini.stefaninifood.controller.dto;

import br.com.stefanini.stefaninifood.model.OrderedItens;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class BuyDTO extends OrderedItensDTO{

    private LocalDateTime orderAt;
    private Integer estimatedTime;
    private LocalDateTime readyAt;

    public BuyDTO(OrderedItens orderedItens) {
        super(orderedItens);
//        if(orderedItens.getOrder().getTime() == null)
        try {
            this.orderAt = orderedItens.getOrder().getTime();
        } catch (Exception e ){
            this.orderAt = LocalDateTime.of(0000,00,00,00,00,00);
        }
//        System.out.println(orderAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")));
    }

    public String getOrderAt() {
//        if (!(orderAt == null))
        return orderAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
//        return LocalDateTime.now();
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

    public String getEstimatedTime() {
        if (estimatedTime == null) return "Não informado";
        return estimatedTime + " minutos";
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.setReadyAt(orderAt.plus(estimatedTime, ChronoUnit.MINUTES));
        this.estimatedTime = estimatedTime;
    }

    public String getReadyAt() {
        if (readyAt == null) return "Não informado";
        return readyAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
    }

    public void setReadyAt(LocalDateTime readyAt) {
        this.readyAt = readyAt;
    }

    public static List<BuyDTO> converter(List<OrderedItens> itens){
        return itens.stream().map(BuyDTO::new).collect(Collectors.toList());
    }
}
