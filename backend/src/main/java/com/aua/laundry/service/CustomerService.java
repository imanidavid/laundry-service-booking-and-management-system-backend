package com.aua.laundry.service;

import com.aua.laundry.entity.Customer;
import com.aua.laundry.repository.AttendantRepository;
import com.aua.laundry.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AttendantRepository attendantRepository;

    public CustomerService(CustomerRepository customerRepository, AttendantRepository attendantRepository) {
        this.customerRepository = customerRepository;
        this.attendantRepository = attendantRepository;
    }

    public String save(Customer customer) {
        customerRepository.save(customer);
        return "Customer saved successfully";
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> getByLastName(String lastName) {
        return customerRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public Optional<List<Customer>> getOfAttendant(UUID attendantId) {
        if (!attendantRepository.existsById(attendantId)) {
            return Optional.empty();
        }
        return Optional.of(customerRepository.findOfAttendant(attendantId));
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> update(UUID id, Customer request) {
        Optional<Customer> optional = customerRepository.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        Customer customer = optional.get();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customerRepository.save(customer);
        return Optional.of(customer);
    }

    public String delete(UUID id) {
        if (!customerRepository.existsById(id)) {
            return "The customer with that id does not exist";
        }
        customerRepository.deleteById(id);
        return "Customer deleted successfully";
    }

}
