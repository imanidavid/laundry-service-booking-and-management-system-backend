package com.aua.laundry.controller;

import com.aua.laundry.entity.Customer;
import com.aua.laundry.service.CustomerService;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Customer customer) {
        String message = customerService.save(customer);
        if (message.equals("customer saved")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<Customer> all() {
        return customerService.findAll();
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Customer>> byLastName(@RequestParam String lastName) {
        List<Customer> customers = customerService.getByLastName(lastName);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/of-attendant/{attendantId}")
    public ResponseEntity<?> ofAttendant(@PathVariable UUID attendantId) {
        Optional<List<Customer>> customers = customerService.getOfAttendant(attendantId);
        if (customers.isEmpty()) {
            return new ResponseEntity<>("attendant with " + attendantId + " dont exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(customers.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Customer> optional = customerService.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("customer with " + id + " dont exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Customer customer) {
        Optional<Customer> optional = customerService.update(id, customer);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("customer with " + id + " dont exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = customerService.delete(id);
        if (message.startsWith("customer with ")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
