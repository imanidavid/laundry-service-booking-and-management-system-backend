package com.aua.laundry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "delivery_note")
public class DeliveryNote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String garmentName;

    private String condition;

    private int pieces;

    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name = "laundry_order_id")
    private LaundryOrder laundryOrder;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGarmentName() {
        return garmentName;
    }

    public void setGarmentName(String garmentName) {
        this.garmentName = garmentName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LaundryOrder getLaundryOrder() {
        return laundryOrder;
    }

    public void setLaundryOrder(LaundryOrder laundryOrder) {
        this.laundryOrder = laundryOrder;
    }

}
