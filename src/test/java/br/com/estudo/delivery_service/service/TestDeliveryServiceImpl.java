package br.com.estudo.delivery_service.service;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.model.DeliveryStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(properties = "spring.kafka.listener.auto-startup=false")
@ActiveProfiles("test")
@Transactional
public class TestDeliveryServiceImpl {
    @Autowired
    private DeliveryService deliveryService;

    @Test
    @DisplayName("Create")
    @Order(1)
    public void testCreateDelivery_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);

        Delivery savedDelivery= deliveryService.createDelivery(delivery);

        Assertions.assertNotNull(savedDelivery);
        Assertions.assertEquals(savedDelivery.getStatus(), delivery.getStatus());
        Assertions.assertEquals(savedDelivery.getOrderId(), delivery.getOrderId());
    }

    @Test
    @DisplayName("Get by id")
    @Order(2)
    public void testGetDeliveryById_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery= deliveryService.createDelivery(delivery);

        Delivery gettedDelivery= deliveryService.getDeliveryById(savedDelivery.getId());

        Assertions.assertNotNull(gettedDelivery);
        Assertions.assertEquals(gettedDelivery.getStatus(), delivery.getStatus());
    }

    @Test
    @DisplayName("Update")
    @Order(3)
    public void testUpdateDelivery_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery= deliveryService.createDelivery(delivery);

        savedDelivery.setStatus(DeliveryStatus.ONGOING);
        Delivery updatedDelivery= deliveryService.updateDelivery(savedDelivery, savedDelivery.getId());

        Assertions.assertNotNull(updatedDelivery);
        Assertions.assertEquals(updatedDelivery.getStatus(), delivery.getStatus());
    }

    @Test
    @DisplayName("Get all")
    @Order(4)
    public void testGetAll_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        deliveryService.createDelivery(delivery);

        List<Delivery> deliveries = deliveryService.getAllDeliveries();

        Assertions.assertNotNull(deliveries);
        Assertions.assertEquals(1, deliveries.size());
    }

    @Test
    @DisplayName("Delete")
    @Order(5)
    public void testDeleteDelivery_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery= deliveryService.createDelivery(delivery);

        deliveryService.deleteDeliveryById(savedDelivery.getId());
    }

    @Test
    @DisplayName("Get all by Status")
    @Order(6)
    public void testGetAllByStatus_WithValidData_ReturnsDelivery() {
        Delivery delivery1 = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        deliveryService.createDelivery(delivery1);
        Delivery delivery2 = new Delivery(1L, DeliveryStatus.ONGOING);
        deliveryService.createDelivery(delivery2);

        List<Delivery> deliveryList= deliveryService.getDeliveriesByStatus(DeliveryStatus.WAITING_DISPATCH.name());

        Assertions.assertNotNull(deliveryList);
        Assertions.assertEquals(1, deliveryList.size());
    }

    @Test
    @DisplayName("Get by order id")
    @Order(7)
    public void testGetDeliveryByOrderId_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery=deliveryService.createDelivery(delivery);

        Delivery gettedDelivery= deliveryService.getDeliveryByOrderId(savedDelivery.getOrderId());

        Assertions.assertNotNull(gettedDelivery);
        Assertions.assertEquals(delivery.getStatus(), gettedDelivery.getStatus());
    }
}
