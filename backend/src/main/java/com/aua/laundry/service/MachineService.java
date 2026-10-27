package com.aua.laundry.service;

import com.aua.laundry.entity.Machine;
import com.aua.laundry.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public String save(Machine machine) {
        machineRepository.save(machine);
        return "Machine saved successfully";
    }

    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    public Optional<Machine> findById(UUID id) {
        return machineRepository.findById(id);
    }

    public Optional<Machine> update(UUID id, Machine request) {
        Optional<Machine> optional = machineRepository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        Machine machine = optional.get();
        machine.setMachineNumber(request.getMachineNumber());
        machine.setName(request.getName());
        machineRepository.save(machine);
        return Optional.of(machine);
    }

    public String delete(UUID id) {
        if (!machineRepository.existsById(id)) {
            return "The machine with that id does not exist";
        }
        machineRepository.deleteById(id);
        return "Machine deleted successfully";
    }

}
