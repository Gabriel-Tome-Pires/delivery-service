package br.com.estudo.delivery_service.service;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.model.DeliveryStatus;
import br.com.estudo.delivery_service.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    DeliveryRepository deliveryRepository;
    KafkaService kafkaService;

    @Override
    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery updateDelivery(Delivery delivery, Long id) {
        Delivery existingDelivery= getDeliveryById(id);
        if(delivery.getStatus()!=null && !existingDelivery.getStatus().equals(delivery.getStatus())){
            existingDelivery.setStatus(delivery.getStatus());
            kafkaService.sendUpdateDelivery(existingDelivery);
        }
        return deliveryRepository.save(existingDelivery);
    }

    @Override
    public void deleteDeliveryById(long id) {
        getDeliveryById(id);
        deliveryRepository.deleteById(id);
    }

    @Override
    public Delivery getDeliveryById(long id) {
        return deliveryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Delivery with id " + id + " was not found"));
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery getDeliveryByOrderId(Long id) {
        return deliveryRepository.findByOrderId(id);
    }

    @Override
    public List<Delivery> getDeliveriesByStatus(String status) {
        DeliveryStatus deliveryStatus= deliveryStatusFromString(status);
        return deliveryRepository.findByStatus(deliveryStatus);
    }

    public DeliveryStatus deliveryStatusFromString(String status) {
        try {
            return DeliveryStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
        }
    }
}
