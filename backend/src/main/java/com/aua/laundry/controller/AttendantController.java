package com.aua.laundry.controller;

import com.aua.laundry.entity.Attendant;
import com.aua.laundry.service.AttendantService;
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
@RequestMapping("/api/attendants")
public class AttendantController {

    private final AttendantService attendantService;

    public AttendantController(AttendantService attendantService) {
        this.attendantService = attendantService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Attendant attendant) {
        String message = attendantService.save(attendant);
        if (message.equals("Attendant saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<Attendant> all() {
        return attendantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Attendant> optional = attendantService.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("The attendant with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Attendant attendant) {
        Optional<Attendant> optional = attendantService.update(id, attendant);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("The attendant with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = attendantService.delete(id);
        if (message.equals("The attendant with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
