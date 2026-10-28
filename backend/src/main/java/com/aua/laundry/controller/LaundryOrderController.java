package com.aua.laundry.controller;

import com.aua.laundry.entity.LaundryOrder;
import com.aua.laundry.entity.LaundryStatus;
import com.aua.laundry.service.LaundryOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/laundry-orders")
public class LaundryOrderController {

    private final LaundryOrderService laundryOrderService;

    public LaundryOrderController(LaundryOrderService laundryOrderService) {
        this.laundryOrderService = laundryOrderService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody LaundryOrder laundryOrder) {
        String message = laundryOrderService.save(laundryOrder);
        if (message.equals("Laundry order saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<LaundryOrder> all() {
        return laundryOrderService.findAll();
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<LaundryOrder>> byStatus(@RequestParam LaundryStatus status) {
        List<LaundryOrder> laundryOrders = laundryOrderService.getByStatus(status);
        return ResponseEntity.ok(laundryOrders);
    }

    @GetMapping("/between")
    public ResponseEntity<List<LaundryOrder>> between(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<LaundryOrder> laundryOrders = laundryOrderService.getBetween(startDate, endDate);
        return ResponseEntity.ok(laundryOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<LaundryOrder> optional = laundryOrderService.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("The laundry order with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody LaundryOrder laundryOrder) {
        Optional<LaundryOrder> optional = laundryOrderService.update(id, laundryOrder);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("The laundry order with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = laundryOrderService.delete(id);
        if (message.equals("The laundry order with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
