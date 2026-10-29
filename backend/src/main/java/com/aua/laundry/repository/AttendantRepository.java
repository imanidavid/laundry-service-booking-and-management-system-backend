package com.aua.laundry.repository;

import com.aua.laundry.entity.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AttendantRepository extends JpaRepository<Attendant, UUID> {

    @Query("""
            SELECT a FROM Attendant a
            JOIN a.serviceTypes s
            WHERE LOWER(s.name) = LOWER(:name)
            """)
    List<Attendant> findByServiceTypeName(@Param("name") String name);

}
