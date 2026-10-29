package com.aua.laundry.repository;

import com.aua.laundry.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MachineRepository extends JpaRepository<Machine, UUID> {

    @Query("""
            SELECT m FROM Machine m
            WHERE NOT EXISTS (SELECT a FROM Attendant a WHERE a.machine = m)
            ORDER BY m.machineNumber
            """)
    List<Machine> findFreeMachines();

}
