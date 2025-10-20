package br.com.estudo.delivery_service.service;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.model.DeliveryStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class KafkaListenerService {
    KafkaService kafkaService;
    DeliveryService deliveryService;

    //Simulate a delivery system
    @KafkaListener(topics = "order-paid", groupId = "delivery_service")
    private void consume(String orderJson){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> orderMap = mapper.readValue(orderJson, Map.class);
            Number orderNumberId=(Number)orderMap.get("id");
            Long orderId=orderNumberId.longValue();

            System.out.println(DeliveryStatus.WAITING_DISPATCH);
            Delivery delivery = deliveryService.createDelivery(new Delivery(orderId, DeliveryStatus.WAITING_DISPATCH));
            kafkaService.sendUpdateDelivery(delivery);

            System.out.println(DeliveryStatus.ONGOING);
            delivery.setStatus(DeliveryStatus.ONGOING);
            kafkaService.sendUpdateDelivery(delivery);

            System.out.println(DeliveryStatus.ARRIVED);
            delivery.setStatus(DeliveryStatus.ARRIVED);
            kafkaService.sendUpdateDelivery(delivery);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
