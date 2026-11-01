package com.aua.laundry.repository;

import com.aua.laundry.entity.LaundryOrder;
import com.aua.laundry.entity.LaundryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LaundryOrderRepository extends JpaRepository<LaundryOrder, UUID> {

    List<LaundryOrder> findByStatusOrderByPickupDateAsc(LaundryStatus status);

    List<LaundryOrder> findByPickupDateBetweenOrderByPickupDateAsc(LocalDate start, LocalDate end);

    boolean existsByAttendant_IdAndPickupDateAndStatusNot(UUID attendantId, LocalDate pickupDate, LaundryStatus status);

    @Query("SELECT l.status, COUNT(l) FROM LaundryOrder l GROUP BY l.status")
    List<Object[]> countByStatus();

    @Modifying
    @Query("UPDATE LaundryOrder l SET l.status = 'CANCELLED' WHERE l.attendant.id = :attendantId AND l.pickupDate = :date AND l.status <> 'READY'")
    int cancelDay(@Param("attendantId") UUID attendantId, @Param("date") LocalDate date);

}
