package com.aua.laundry.service;

import com.aua.laundry.entity.ServiceType;
import com.aua.laundry.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public String save(ServiceType serviceType) {
        serviceTypeRepository.save(serviceType);
        return "Service type saved successfully";
    }

    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    public List<ServiceType> getUnused() {
        return serviceTypeRepository.findUnused();
    }

    public Optional<ServiceType> findById(UUID id) {
        return serviceTypeRepository.findById(id);
    }

    public Optional<ServiceType> update(UUID id, ServiceType request) {
        Optional<ServiceType> optional = serviceTypeRepository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        ServiceType serviceType = optional.get();
        serviceType.setName(request.getName());
        serviceTypeRepository.save(serviceType);
        return Optional.of(serviceType);
    }

    public String delete(UUID id) {
        if (!serviceTypeRepository.existsById(id)) {
            return "The service type with that id does not exist";
        }
        serviceTypeRepository.deleteById(id);
        return "Service type deleted successfully";
    }

}
