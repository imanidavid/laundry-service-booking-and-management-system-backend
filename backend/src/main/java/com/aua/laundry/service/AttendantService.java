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
        return "attendant saved";
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
            return "attendant with " + attendantId + " dont exist";
        }
        Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(serviceTypeId);
        if (serviceTypeOptional.isEmpty()) {
            return "service type with " + serviceTypeId + " dont exist";
        }
        Attendant attendant = attendantRepository.findById(attendantId).get();
        for (ServiceType serviceType : attendant.getServiceTypes()) {
            if (serviceType.getId().equals(serviceTypeId)) {
                return "attendant already has service type";
            }
        }
        attendant.getServiceTypes().add(serviceTypeOptional.get());
        attendantRepository.save(attendant);
        return "service type added";
    }

    public String moveAttendantToMachine(UUID attendantId, int machineNumber) {
        if (!attendantRepository.existsById(attendantId)) {
            return "attendant with " + attendantId + " dont exist";
        }
        Optional<Machine> machineOptional = machineRepository.findByMachineNumber(machineNumber);
        if (machineOptional.isEmpty()) {
            return "machine with " + machineNumber + " dont exist";
        }
        Machine machine = machineOptional.get();
        Attendant attendant = attendantRepository.findById(attendantId).get();
        if (attendant.getMachine() != null && attendant.getMachine().getMachineNumber() == machineNumber) {
            return "machine changed";
        }
        if (attendantRepository.existsByMachine(machine)) {
            return "machine taken by another attendant";
        }
        attendant.setMachine(machine);
        attendantRepository.save(attendant);
        return "machine changed";
    }

    public String delete(UUID id) {
        if (!attendantRepository.existsById(id)) {
            return "attendant with " + id + " dont exist";
        }
        attendantRepository.deleteById(id);
        return "attendant deleted";
    }

}
