package com.neslihansezen.ipcs.data.repository;

import com.neslihansezen.ipcs.data.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    Optional<VehicleType> findByShortNameOrLongName(String shortName, String longName);
}
