package com.aua.laundry.service;

import com.aua.laundry.entity.Attendant;
import com.aua.laundry.entity.Customer;
import com.aua.laundry.entity.LaundryOrder;
import com.aua.laundry.repository.AttendantRepository;
import com.aua.laundry.repository.CustomerRepository;
import com.aua.laundry.repository.LaundryOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaundryOrderService {

    private final LaundryOrderRepository laundryOrderRepository;
    private final CustomerRepository customerRepository;
    private final AttendantRepository attendantRepository;

    public LaundryOrderService(LaundryOrderRepository laundryOrderRepository,
                               CustomerRepository customerRepository,
                               AttendantRepository attendantRepository) {
        this.laundryOrderRepository = laundryOrderRepository;
        this.customerRepository = customerRepository;
        this.attendantRepository = attendantRepository;
    }

    public String save(LaundryOrder laundryOrder) {
        laundryOrderRepository.save(laundryOrder);
        return "Laundry order saved successfully";
    }

    public List<LaundryOrder> findAll() {
        return laundryOrderRepository.findAll();
    }

    public Optional<LaundryOrder> findById(UUID id) {
        return laundryOrderRepository.findById(id);
    }

    public Optional<LaundryOrder> update(UUID id, LaundryOrder request) {
        Optional<LaundryOrder> optional = laundryOrderRepository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        LaundryOrder laundryOrder = optional.get();
        laundryOrder.setPickupDate(request.getPickupDate());
        laundryOrder.setStatus(request.getStatus());
        laundryOrder.setBags(request.getBags());
        laundryOrder.setWeightKg(request.getWeightKg());
        if (request.getCustomer() != null && request.getCustomer().getId() != null) {
            Optional<Customer> customerOptional = customerRepository.findById(request.getCustomer().getId());
            if (customerOptional.isPresent()) {
                laundryOrder.setCustomer(customerOptional.get());
            } else {
                laundryOrder.setCustomer(request.getCustomer());
            }
        } else {
            laundryOrder.setCustomer(null);
        }
        if (request.getAttendant() != null && request.getAttendant().getId() != null) {
            Optional<Attendant> attendantOptional = attendantRepository.findById(request.getAttendant().getId());
            if (attendantOptional.isPresent()) {
                laundryOrder.setAttendant(attendantOptional.get());
            } else {
                laundryOrder.setAttendant(request.getAttendant());
            }
        } else {
            laundryOrder.setAttendant(null);
        }
        laundryOrderRepository.save(laundryOrder);
        return Optional.of(laundryOrder);
    }

    public String delete(UUID id) {
        if (!laundryOrderRepository.existsById(id)) {
            return "The laundry order with that id does not exist";
        }
        laundryOrderRepository.deleteById(id);
        return "Laundry order deleted successfully";
    }

}
