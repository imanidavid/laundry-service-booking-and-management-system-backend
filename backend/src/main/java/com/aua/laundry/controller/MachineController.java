package com.aua.laundry.controller;

import com.aua.laundry.entity.Machine;
import com.aua.laundry.service.MachineService;
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
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Machine machine) {
        String message = machineService.save(machine);
        if (message.equals("Machine saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public List<Machine> all() {
        return machineService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        Optional<Machine> optional = machineService.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("The machine with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Machine machine) {
        Optional<Machine> optional = machineService.update(id, machine);
        if (optional.isEmpty()) {
            return new ResponseEntity<>("The machine with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = machineService.delete(id);
        if (message.equals("The machine with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
