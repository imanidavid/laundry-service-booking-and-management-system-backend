package com.aua.laundry.repository;

import com.aua.laundry.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("""
            SELECT DISTINCT o.customer FROM LaundryOrder o
            WHERE o.attendant.id = :attendantId
            """)
    List<Customer> findOfAttendant(@Param("attendantId") UUID attendantId);

}
