package com.neslihansezen.ipcs.api.entity;

import com.neslihansezen.ipcs.api.dto.TransactionRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_typ", nullable = false, length = 10)
    private String vehicle;

    @Column(name = "mileage", nullable = false, length = 6)
    private int mileage;

    @Column(name = "postcode", nullable = false, length = 5)
    private String postcode;

    @Column(name = "insurance_premium", nullable = false)
    private double insurancePremium;

    @Column(name = "source", nullable = false)
    private String source;

    @CreatedDate
    @Column(name = "date")
    private LocalDateTime date;

    public Transaction(TransactionRequest request, double insurancePremium, String source) {
        this.vehicle = request.getVehicle();
        this.mileage = Integer.parseInt(request.getMileage());
        this.postcode = request.getPostcode();
        this.insurancePremium = insurancePremium;
        this.source = source;
        this.date = LocalDateTime.now();
    }
}
