package com.aua.laundry.controller;

import com.aua.laundry.entity.DeliveryNote;
import com.aua.laundry.service.DeliveryNoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-notes")
public class DeliveryNoteController {

    private final DeliveryNoteService deliveryNoteService;

    public DeliveryNoteController(DeliveryNoteService deliveryNoteService) {
        this.deliveryNoteService = deliveryNoteService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam UUID laundryOrderId, @RequestBody DeliveryNote deliveryNote) {
        String message = deliveryNoteService.save(deliveryNote, laundryOrderId);
        if (message.startsWith("laundry with ")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("delivery notes only for READY orders")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/customer/{phone}")
    public ResponseEntity<List<DeliveryNote>> byCustomer(@PathVariable String phone) {
        List<DeliveryNote> deliveryNotes = deliveryNoteService.getByCustomerPhone(phone);
        return ResponseEntity.ok(deliveryNotes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = deliveryNoteService.delete(id);
        if (message.startsWith("delivery note with ")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
