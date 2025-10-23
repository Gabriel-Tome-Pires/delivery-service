package br.com.estudo.delivery_service.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import br.com.estudo.delivery_service.model.Delivery;

@Service
@Profile("test")
public class FakeKafkaService extends KafkaService {

    public FakeKafkaService() {
        super(null);
    }

    @Override
    public void sendUpdateDelivery(Delivery delivery) {
        System.out.println("[FakeKafkaService] pretending to update delivery...");
    }
}
