package com.aua.laundry.service;

import com.aua.laundry.entity.Attendant;
import com.aua.laundry.entity.Machine;
import com.aua.laundry.entity.ServiceType;
import com.aua.laundry.repository.AttendantRepository;
import com.aua.laundry.repository.MachineRepository;
import com.aua.laundry.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendantService {

    private final AttendantRepository attendantRepository;
    private final MachineRepository machineRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public AttendantService(AttendantRepository attendantRepository, MachineRepository machineRepository,
                            ServiceTypeRepository serviceTypeRepository) {
        this.attendantRepository = attendantRepository;
        this.machineRepository = machineRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public String save(Attendant attendant) {
        attendantRepository.save(attendant);
        return "Attendant saved successfully";
    }

    public List<Attendant> findAll() {
        return attendantRepository.findAll();
    }

    public List<Attendant> getByServiceTypeName(String name) {
        return attendantRepository.findByServiceTypeName(name);
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

    public String addServiceType(UUID attendantId, UUID serviceTypeId) {
        if (!attendantRepository.existsById(attendantId)) {
            return "The attendant with that id does not exist";
        }
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(serviceTypeId);
        if (serviceTypeOptional.isEmpty()) {
            return "That service type does not exist";
        }
        Attendant attendant = attendantRepository.findById(attendantId).get();
        for (ServiceType serviceType : attendant.getServiceTypes()) {
            if (serviceType.getId().equals(serviceTypeId)) {
                return "The attendant already has that service type";
            }
        }
        attendant.getServiceTypes().add(serviceTypeOptional.get());
        attendantRepository.save(attendant);
        return "Service type added to the attendant";
    }

    public String moveAttendantToMachine(UUID attendantId, int machineNumber) {
        if (!attendantRepository.existsById(attendantId)) {
            return "The attendant with that id does not exist";
        }
        Optional<Machine> machineOptional = machineRepository.findByMachineNumber(machineNumber);
        if (machineOptional.isEmpty()) {
            return "The machine with that machineNumber does not exist";
        }
        Machine machine = machineOptional.get();
        Attendant attendant = attendantRepository.findById(attendantId).get();
        if (attendant.getMachine() != null && attendant.getMachine().getMachineNumber() == machineNumber) {
            return "Machine changed successfully";
        }
        if (attendantRepository.existsByMachine(machine)) {
            return "That machine is taken by another attendant";
        }
        attendant.setMachine(machine);
        attendantRepository.save(attendant);
        return "Machine changed successfully";
    }

    public String delete(UUID id) {
        if (!attendantRepository.existsById(id)) {
            return "The attendant with that id does not exist";
        }
        attendantRepository.deleteById(id);
        return "Attendant deleted succesfuly";
    }

}
