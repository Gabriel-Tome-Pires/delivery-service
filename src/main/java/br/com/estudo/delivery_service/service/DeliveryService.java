package br.com.estudo.delivery_service.service;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.model.DeliveryStatus;

import java.util.List;

public interface DeliveryService {
    Delivery createDelivery(Delivery delivery);
    Delivery updateDelivery(Delivery delivery, Long id);
    void deleteDeliveryById(long id);
    Delivery getDeliveryById(long id);
    List<Delivery> getAllDeliveries();
    Delivery getDeliveryByOrderId(Long id);
    List<Delivery> getDeliveriesByStatus(String status);
}
