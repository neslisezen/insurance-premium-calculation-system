package com.neslihansezen.ipcs.data.service;

import com.neslihansezen.ipcs.data.entity.RegionMap;
import com.neslihansezen.ipcs.data.exception.PostcodeNotFoundException;
import com.neslihansezen.ipcs.data.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.neslihansezen.ipcs.data.constants.Messages.POSTCODE_NOT_FOUND;

/**
 * The RegionService class handles operations related to regions.
 * This service provides functionality to retrieve the region factor based on a postal code.
 * It also caches the region factor to optimize performance for frequent queries.
 *
 * @see RegionRepository
 * @see RegionMap
 * @see PostcodeNotFoundException
 */
@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository repository;

    /**
     * Retrieves the region factor for the given postal code.
     *
     * This method checks if the region factor is available for the provided postal code
     * and returns it. If the region is not found, a {@see PostcodeNotFoundException} is thrown.
     * The result is cached to improve performance for repeated queries with the same postal code.
     *
     * @param postcode The postal code for which the region factor is to be fetched.
     * @return The region factor for the given postal code.
     * @throws PostcodeNotFoundException If no region is found for the given postal code.
     * @see RegionRepository#findRegionFactorByPostcode(String postcode)
     */
    @Cacheable("regionFactor")
    public double getFactorByPostcode(String postcode) {
        return repository.findRegionFactorByPostcode(postcode).map(RegionMap::getRegionFactor)
                .orElseThrow(() -> new PostcodeNotFoundException(POSTCODE_NOT_FOUND + ": " + postcode));
    }
}


