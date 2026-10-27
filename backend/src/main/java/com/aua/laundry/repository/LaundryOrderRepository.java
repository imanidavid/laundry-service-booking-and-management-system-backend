package com.aua.laundry.repository;

import com.aua.laundry.entity.LaundryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LaundryOrderRepository extends JpaRepository<LaundryOrder, UUID> {
}
