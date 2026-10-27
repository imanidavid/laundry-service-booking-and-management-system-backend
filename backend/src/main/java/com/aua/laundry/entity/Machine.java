package com.aua.laundry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int machineNumber;

    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
