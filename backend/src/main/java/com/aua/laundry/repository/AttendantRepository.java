package com.aua.laundry.repository;

import com.aua.laundry.entity.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendantRepository extends JpaRepository<Attendant, UUID> {
}
