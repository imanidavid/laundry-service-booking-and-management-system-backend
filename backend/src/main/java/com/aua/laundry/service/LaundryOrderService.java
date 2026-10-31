package com.aua.laundry.service;

import com.aua.laundry.entity.Attendant;
import com.aua.laundry.entity.Customer;
import com.aua.laundry.entity.LaundryOrder;
import com.aua.laundry.entity.LaundryStatus;
import com.aua.laundry.repository.AttendantRepository;
import com.aua.laundry.repository.CustomerRepository;
import com.aua.laundry.repository.DeliveryNoteRepository;
import com.aua.laundry.repository.LaundryOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaundryOrderService {

    private final LaundryOrderRepository laundryOrderRepository;
    private final CustomerRepository customerRepository;
    private final AttendantRepository attendantRepository;
    private final DeliveryNoteRepository deliveryNoteRepository;

    public LaundryOrderService(LaundryOrderRepository laundryOrderRepository,
                               CustomerRepository customerRepository,
                               AttendantRepository attendantRepository,
                               DeliveryNoteRepository deliveryNoteRepository) {
        this.laundryOrderRepository = laundryOrderRepository;
        this.customerRepository = customerRepository;
        this.attendantRepository = attendantRepository;
        this.deliveryNoteRepository = deliveryNoteRepository;
    }

    public String save(LaundryOrder laundryOrder) {
        if (laundryOrder.getPickupDate() == null || laundryOrder.getPickupDate().isBefore(LocalDate.now())) {
            return "laundry date cannot be in past";
        }
        if (laundryOrder.getAttendant() != null && laundryOrder.getAttendant().getId() != null
                && laundryOrderRepository.existsByAttendant_IdAndPickupDateAndStatusNot(
                laundryOrder.getAttendant().getId(), laundryOrder.getPickupDate(), LaundryStatus.CANCELLED)) {
            return "attendant already has order on that date";
        }
        laundryOrder.setStatus(LaundryStatus.RECEIVED);
        laundryOrderRepository.save(laundryOrder);
        return "laundry saved";
    }

    public List<LaundryOrder> findAll() {
        return laundryOrderRepository.findAll();
    }

    public List<LaundryOrder> getByStatus(LaundryStatus status) {
        return laundryOrderRepository.findByStatusOrderByPickupDateAsc(status);
    }

    public List<LaundryOrder> getBetween(LocalDate start, LocalDate end) {
        return laundryOrderRepository.findByPickupDateBetweenOrderByPickupDateAsc(start, end);
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

    public String changeStatus(UUID id, LaundryStatus newStatus) {
        Optional<LaundryOrder> optional = laundryOrderRepository.findById(id);
        if (optional.isEmpty()) {
            return "laundry with " + id + " dont exist";
        }
        LaundryOrder laundryOrder = optional.get();
        LaundryStatus current = laundryOrder.getStatus();
        boolean allowed = false;
        if (current == LaundryStatus.RECEIVED && (newStatus == LaundryStatus.WASHING || newStatus == LaundryStatus.CANCELLED)) {
            allowed = true;
        }
        if (current == LaundryStatus.WASHING && (newStatus == LaundryStatus.READY || newStatus == LaundryStatus.CANCELLED)) {
            allowed = true;
        }
        if (!allowed) {
            return "cannot change status from " + current + " to " + newStatus;
        }
        laundryOrder.setStatus(newStatus);
        laundryOrderRepository.save(laundryOrder);
        return "laundry status updated";
    }

    public String delete(UUID id) {
        Optional<LaundryOrder> optional = laundryOrderRepository.findById(id);
        if (optional.isEmpty()) {
            return "laundry with " + id + " dont exist";
        }
        if (deliveryNoteRepository.existsByLaundryOrder(optional.get())) {
            return "delete delivery notes first";
        }
        laundryOrderRepository.deleteById(id);
        return "laundry deleted";
    }

}
