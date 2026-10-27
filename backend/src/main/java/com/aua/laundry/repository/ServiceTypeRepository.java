package com.aua.laundry.repository;

import com.aua.laundry.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {
}
