package com.adamszablewski.facilities.service;

import com.adamszablewski.appointments.dtos.RestResponseDTO;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.facilities.employmentRequests.EmploymentRequestService;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Owner;
import com.adamszablewski.messages.MessageSender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import com.adamszablewski.tasks.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@AllArgsConstructor
public class FacilityService {

    private final UserServiceClient userServiceClient;
    private final FacilityRepository facilityRepository;
    private final EmploymentRequestService employmentRequestService;
    private final MessageSender messageSender;
    @PersistenceContext
    private EntityManager entityManager;
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    public List<Facility> getAllFacilitiesForRegion(String region) {
        return facilityRepository.findByRegion(region);
    }

    public List<Facility> getAllFacilitiesForCity(String city) {
        return facilityRepository.findByCity(city);
    }

    public Facility getFacilityById(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
    }
    @Transactional
    public void createFacility(Facility facility, String ownerEmail) {
        RestResponseDTO<Owner> responseDTO = userServiceClient.findOwnerByEmail(ownerEmail);
        Owner owner = responseDTO.getValue();
        if (owner == null){
            throw new NoSuchUserException();
        }
        owner = entityManager.merge(owner);

        Facility newFacility = Facility.builder()
                .name(facility.getName())
                .country(facility.getCountry())
                .region(facility.getRegion())
                .city(facility.getCity())
                .houseNumber(facility.getHouseNumber())
                .street(facility.getStreet())
                .owner(owner)
                .build();

        facilityRepository.save(newFacility);
        messageSender.createFacilityCreatedMessage(ownerEmail, facility);

    }

    public void addServiceToFacility(Long id, Task service) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        facility.getTasks().add(service);
        facilityRepository.save(facility);
    }

    public void removeFacilityById(Long id) {
        facilityRepository.deleteById(id);
    }

    public void addEmployeeToFacility(String email, long facilityId) {
        employmentRequestService.sendEmploymentRequest(email, facilityId);
    }
}
