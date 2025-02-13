package com.neslihansezen.ipcs.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Builder
@Getter
@RequiredArgsConstructor
@Table(name = "postcode_region_map")
@AllArgsConstructor
public class RegionMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postcode", nullable = false, unique = true)
    private String postcode;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "region_factor", nullable = false)
    private double regionFactor;
}
