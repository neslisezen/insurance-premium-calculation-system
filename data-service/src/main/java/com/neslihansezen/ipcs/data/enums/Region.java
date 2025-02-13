package com.neslihansezen.ipcs.data.enums;

import com.neslihansezen.ipcs.data.exception.RegionNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.neslihansezen.ipcs.data.constants.Messages.REGION_NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum Region {
    BY("Bayern", 10.0),
    BW("Baden-Württemberg", 9.00),
    HH("Hamburg", 8.0),
    HE("Hessen", 7.0),
    BE("Berlin", 6.75),
    RP("Rheinland-Pfalz", 6.50),
    NW("Nordrhein-Westfalen", 6.25),
    SH("Schleswig-Holstein", 6.0),
    NI("Niedersachsen", 5.90),
    SN("Sachsen", 5.80),
    HB("Bremen", 5.70),
    SL("Saarland", 5.60),
    TH("Thüringen", 5.50),
    ST("Sachsen-Anhalt", 5.40),
    BB("Brandenburg", 5.30),
    MV("Mecklenburg-Vorpommern", 5.20);

    private final String region;
    private final double factor;

    /**
     * Retrieves the factor associated with a specific region.
     *
     * @param region the name of the region whose factor is to be retrieved
     * @return the factor value for the specified region
     * @throws RegionNotFoundException if the region is not found in the defined regions
     */
    public static double getFactorByRegion(String region) {
        return Arrays.stream(Region.values())
                .filter(r -> r.getRegion().equals(region))
                .findFirst().map(Region::getFactor)
                .orElseThrow(() -> new RegionNotFoundException(REGION_NOT_FOUND));
    }

}

