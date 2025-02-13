package com.neslihansezen.ipcs.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "vehicle_type")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_name", nullable = false, length = 10)
    private String shortName;

    @Column(name = "long_name", nullable = false, length = 25)
    private String longName;

    @Column(name = "factor", nullable = false)
    private double factor;

}
