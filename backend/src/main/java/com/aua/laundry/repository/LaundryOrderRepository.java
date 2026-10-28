package com.aua.laundry.repository;

import com.aua.laundry.entity.LaundryOrder;
import com.aua.laundry.entity.LaundryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LaundryOrderRepository extends JpaRepository<LaundryOrder, UUID> {

    List<LaundryOrder> findByStatusOrderByPickupDateAsc(LaundryStatus status);

    List<LaundryOrder> findByPickupDateBetweenOrderByPickupDateAsc(LocalDate start, LocalDate end);

}
