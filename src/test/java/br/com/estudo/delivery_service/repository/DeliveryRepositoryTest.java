package br.com.estudo.delivery_service.repository;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.model.DeliveryStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(properties = "spring.kafka.listener.auto-startup=false")
@ActiveProfiles("test")
@Transactional
public class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    @DisplayName("Create")
    @Order(1)
    public void testCreateDelivery_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);

        Delivery savedDelivery= deliveryRepository.save(delivery);

        Assertions.assertNotNull(savedDelivery);
        Assertions.assertEquals(savedDelivery.getStatus(), delivery.getStatus());
        Assertions.assertEquals(savedDelivery.getOrderId(), delivery.getOrderId());
    }

    @Test
    @DisplayName("Get by id")
    @Order(2)
    public void testGetDeliveryById_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        deliveryRepository.save(delivery);

        Delivery gettedDelivery= deliveryRepository.findById(delivery.getId()).orElse(null);

        Assertions.assertNotNull(gettedDelivery);
        Assertions.assertEquals(gettedDelivery.getStatus(), delivery.getStatus());
    }

    @Test
    @DisplayName("Update")
    @Order(3)
    public void testUpdateDelivery_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery= deliveryRepository.save(delivery);
        savedDelivery.setStatus(DeliveryStatus.ONGOING);

        deliveryRepository.save(savedDelivery);
        Delivery updatedDelivery= deliveryRepository.findById(savedDelivery.getId()).orElse(null);

        Assertions.assertNotNull(updatedDelivery);
        Assertions.assertEquals(updatedDelivery.getStatus(), savedDelivery.getStatus());
    }

    @Test
    @DisplayName("Get all")
    @Order(4)
    public void testGetAll_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        deliveryRepository.save(delivery);

        List<Delivery> deliveryList= deliveryRepository.findAll();

        Assertions.assertNotNull(deliveryList);
        Assertions.assertEquals(1, deliveryList.size());
    }

    @Test
    @DisplayName("Delete")
    @Order(5)
    public void testDeleteDelivery_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery=deliveryRepository.save(delivery);

        deliveryRepository.deleteById(savedDelivery.getId());
    }

    @Test
    @DisplayName("Get all by Status")
    @Order(6)
    public void testGetAllByStatus_WithValidData_ReturnsDelivery() {
        Delivery delivery1 = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        deliveryRepository.save(delivery1);
        Delivery delivery2 = new Delivery(1L, DeliveryStatus.ONGOING);
        deliveryRepository.save(delivery2);

        List<Delivery> deliveryList= deliveryRepository.findByStatus(DeliveryStatus.WAITING_DISPATCH);

        Assertions.assertNotNull(deliveryList);
        Assertions.assertEquals(1, deliveryList.size());
    }

    @Test
    @DisplayName("Get by order id")
    @Order(7)
    public void testGetDeliveryByOrderId_WithValidData_ReturnsDelivery() {
        Delivery delivery = new Delivery(1L, DeliveryStatus.WAITING_DISPATCH);
        Delivery savedDelivery=deliveryRepository.save(delivery);

        Delivery gettedDelivery= deliveryRepository.findByOrderId(savedDelivery.getOrderId());

        Assertions.assertNotNull(gettedDelivery);
        Assertions.assertEquals(delivery.getStatus(), gettedDelivery.getStatus());
    }
}
