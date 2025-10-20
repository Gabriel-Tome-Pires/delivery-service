package br.com.estudo.delivery_service.controller;

import br.com.estudo.delivery_service.model.Delivery;
import br.com.estudo.delivery_service.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {
   private DeliveryService deliveryService;

   @PostMapping
   public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
       return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.createDelivery(delivery));
   }

   @PutMapping("/{id}")
   public ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery delivery, @PathVariable Long id) {
       return ResponseEntity.status(HttpStatus.OK).body(deliveryService.updateDelivery(delivery, id));
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Delivery> deleteDelivery(@PathVariable Long id) {
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

   @GetMapping("/{id}")
   public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
       return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getDeliveryById(id));
   }

   @GetMapping
   public ResponseEntity<List<Delivery>> getAllDelivery() {
       return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getAllDeliveries());
   }

   @GetMapping("/byStatus/{status}")
   public ResponseEntity<List<Delivery>> getDeliveryByStatus(@PathVariable String status) {
       return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getDeliveriesByStatus(status));
   }

    @GetMapping("/byOrder/{id}")
    public ResponseEntity<Delivery> getDeliveryByOrder(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(deliveryService.getDeliveryByOrderId(id));
    }
}
