package br.com.estudo.delivery_service.service;

import br.com.estudo.delivery_service.model.Delivery;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Profile("!test")
public class KafkaService {
    private final KafkaTemplate<String, Delivery> kafkaTemplateDelivery;

    @SuppressWarnings("null")
    void sendUpdateDelivery(Delivery delivery) {
        this.kafkaTemplateDelivery.send("update-delivery", delivery).
           whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send delivery update: " + ex.getMessage());
            } else {
                System.out.println("Delivery update sent successfully: " + delivery.getId());
            }
        });
    }
}
