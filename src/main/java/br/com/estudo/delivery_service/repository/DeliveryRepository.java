package br.com.estudo.delivery_service.repository;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(Long orderId);
    List<Delivery> findByStatus(DeliveryStatus status);
}
