package com.adamszablewski.facilities.service;

import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.repository.FacilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.adamszablewski.tasks.Task;



import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    public List<Facility> getAllFacilitiesForRegion(String region) {
        return facilityRepository.findByRegion(region);
    }

    public List<Facility> getAllFacilitiesForCity(String city) {
        return facilityRepository.findByCity(city);
    }

    public Optional<Facility> getFacilityById(Long id) {
        return facilityRepository.findById(id);
    }

    public ResponseEntity<String> createFacility(Facility facility) {
        facilityRepository.save(facility);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> addServiceToFacility(Long id, Task service) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        facility.getTasks().add(service);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> removeFacilityById(Long id) {
        facilityRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
