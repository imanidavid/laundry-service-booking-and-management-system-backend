package com.aua.laundry.repository;

import com.aua.laundry.entity.DeliveryNote;
import com.aua.laundry.entity.LaundryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, UUID> {

    @Query("SELECT d FROM DeliveryNote d WHERE d.laundryOrder.customer.phone = :phone ORDER BY d.deliveryDate DESC")
    List<DeliveryNote> findByCustomerPhone(@Param("phone") String phone);

    boolean existsByLaundryOrder(LaundryOrder laundryOrder);

}
