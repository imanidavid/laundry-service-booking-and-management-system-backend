package com.aua.laundry.controller;

import com.aua.laundry.entity.ServiceType;
import com.aua.laundry.service.ServiceTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ServiceType serviceType) {
        String message = serviceTypeService.save(serviceType);
        if (message.equals("service type saved")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<ServiceType> all() {
        return serviceTypeService.findAll();
    }

    @GetMapping("/unused")
    public ResponseEntity<List<ServiceType>> unused() {
        List<ServiceType> serviceTypes = serviceTypeService.getUnused();
        return ResponseEntity.ok(serviceTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<ServiceType> optional = serviceTypeService.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("service type with " + id + " dont exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody ServiceType serviceType) {
        Optional<ServiceType> optional = serviceTypeService.update(id, serviceType);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("service type with " + id + " dont exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = serviceTypeService.delete(id);
        if (message.startsWith("service type with ")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
