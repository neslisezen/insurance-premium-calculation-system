package com.neslihansezen.ipcs.data.repository;

import com.neslihansezen.ipcs.data.entity.RegionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionMap, Long> {


    Optional<RegionMap> findRegionFactorByPostcode(String postcode);
}
