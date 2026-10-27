package com.aua.laundry.repository;

import com.aua.laundry.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MachineRepository extends JpaRepository<Machine, UUID> {
}
