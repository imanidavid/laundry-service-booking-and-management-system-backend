package com.aua.laundry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "laundry_order")
public class LaundryOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate pickupDate;

    @Enumerated(EnumType.STRING)
    private LaundryStatus status;

    private int bags;

    private double weightKg;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "attendant_id")
    private Attendant attendant;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LaundryStatus getStatus() {
        return status;
    }

    public void setStatus(LaundryStatus status) {
        this.status = status;
    }

    public int getBags() {
        return bags;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Attendant getAttendant() {
        return attendant;
    }

    public void setAttendant(Attendant attendant) {
        this.attendant = attendant;
    }

}
