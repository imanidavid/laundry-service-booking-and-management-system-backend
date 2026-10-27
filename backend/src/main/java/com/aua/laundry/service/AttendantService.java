package com.aua.laundry.service;

import com.aua.laundry.entity.Attendant;
import com.aua.laundry.entity.Machine;
import com.aua.laundry.repository.AttendantRepository;
import com.aua.laundry.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendantService {

    private final AttendantRepository attendantRepository;
    private final MachineRepository machineRepository;

    public AttendantService(AttendantRepository attendantRepository, MachineRepository machineRepository) {
        this.attendantRepository = attendantRepository;
        this.machineRepository = machineRepository;
    }

    public String save(Attendant attendant) {
        attendantRepository.save(attendant);
        return "Attendant saved successfully";
    }

    public List<Attendant> findAll() {
        return attendantRepository.findAll();
    }

    public Optional<Attendant> findById(UUID id) {
        return attendantRepository.findById(id);
    }

    public Optional<Attendant> update(UUID id, Attendant request) {
        Optional<Attendant> optional = attendantRepository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        Attendant attendant = optional.get();
        attendant.setFirstName(request.getFirstName());
        attendant.setLastName(request.getLastName());
        attendant.setPhone(request.getPhone());
        if (request.getMachine() != null && request.getMachine().getId() != null) {
            Optional<Machine> machineOptional = machineRepository.findById(request.getMachine().getId());
            if (machineOptional.isPresent()) {
                attendant.setMachine(machineOptional.get());
            } else {
                attendant.setMachine(request.getMachine());
            }
        } else {
            attendant.setMachine(null);
        }
        if (request.getServiceTypes() != null) {
            attendant.getServiceTypes().clear();
            attendant.getServiceTypes().addAll(request.getServiceTypes());
        }
        attendantRepository.save(attendant);
        return Optional.of(attendant);
    }

    public String delete(UUID id) {
        if (!attendantRepository.existsById(id)) {
            return "The attendant with that id does not exist";
        }
        attendantRepository.deleteById(id);
        return "Attendant deleted successfully";
    }

}
