package com.neslihansezen.ipcs.data.loader;

import com.neslihansezen.ipcs.data.entity.VehicleType;
import com.neslihansezen.ipcs.data.repository.VehicleTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * This class ensures that a predefined set of vehicle types is inserted into the database if
 * the `vehicle_type` table is initially empty. The vehicle types include various categories such as
 * "LKW", "BUS", "WOHNMOBIL", and others, each with a specific short name, long name, and factor.
 *<p>
 * Constructor Parameters:
 * - {@code VehicleTypeRepository vehicleTypeRepository}: A repository interface used to interact
 *   with the `vehicle_type` database table.
 *<p>
 * Behavior:
 * - Invoked automatically after the component is initialized.
 * - Checks if the database already contains vehicle types by checking the count of entries in the repository.
 * - If no entries exist, it inserts a predefined list of vehicle types into the database.
 * - If entries already exist, it skips the loading process.
 *<p>
 * Repository:
 * - Interacts with the {@link VehicleTypeRepository} interface to check and save vehicle data.
 */
@Component
@RequiredArgsConstructor
public class VehicleDataLoader {

    private final VehicleTypeRepository vehicleTypeRepository;

    @PostConstruct
    public void loadVehicleTypes() {
        if (vehicleTypeRepository.count() == 0) {
            System.out.println("Vehicle types loading...");
            vehicleTypeRepository.saveAll(List.of(
                    VehicleType.builder().shortName("LKW").longName("LASTKRAFTWAGEN").factor(2.25).build(),
                    VehicleType.builder().shortName("BUS").longName("OMNIBUS").factor(2.0).build(),
                    VehicleType.builder().shortName("WOHNMOBIL").longName("REISEMOBIL").factor(1.75).build(),
                    VehicleType.builder().shortName("CABRIO").longName("CABRIOLET").factor(1.5).build(),
                    VehicleType.builder().shortName("PKW").longName("PERSONENKRAFTWAGEN").factor(1.0).build(),
                    VehicleType.builder().shortName("SUV").longName("SPORTUTILITYVEHICLE").factor(1.25).build(),
                    VehicleType.builder().shortName("KRAD").longName("MOTORRAD").factor(0.5).build()
            ));
        } else {
            System.out.println("Vehicle types already loaded");
            return;
        }
        System.out.println("Vehicle types loaded successfully");
    }
}
