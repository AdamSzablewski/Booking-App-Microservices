package com.adamszablewski.service;

import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.helpers.UserTools;
import com.adamszablewski.exceptions.FacilityNameTakenException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.model.Facility;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.model.Owner;
import com.adamszablewski.messages.MessageSender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import com.adamszablewski.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

import static com.adamszablewski.dto.mapper.Mapper.mapFacilityToDto;

@Service
@AllArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final EmploymentRequestService employmentRequestService;
    private final MessageSender messageSender;
    private final UserTools userTools;
    @PersistenceContext
    private EntityManager entityManager;
    public Set<FacilityDto> getAllFacilities() {
        return mapFacilityToDto((Set<Facility>) facilityRepository.findAll());
    }

    public Set<FacilityDto> getAllFacilitiesForRegion(String region) {
        return mapFacilityToDto(facilityRepository.findByRegion(region));
    }

    public Set<FacilityDto> getAllFacilitiesForCity(String city) {
        return mapFacilityToDto(facilityRepository.findByCity(city));
    }

    public FacilityDto getFacilityById(Long id) {
        return mapFacilityToDto(facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new));
    }
    @Transactional
    public void createFacility(Facility facility, String email) {

        if (facilityRepository.existsByName(facility.getName())){
            throw new FacilityNameTakenException();
        }
        Owner owner = userTools.getOwnerByEmail(email);
        Facility newFacility = Facility.builder()
                .name(facility.getName())
                .country(facility.getCountry())
                .region(facility.getRegion())
                .city(facility.getCity())
                .houseNumber(facility.getHouseNumber())
                .street(facility.getStreet())
                .owner(owner)
                .build();

        if(owner.getFacilities() != null){
            owner.getFacilities().add(newFacility);
        }
        else {
            owner.setFacilities(Set.of((newFacility)));
        }

        facilityRepository.save(newFacility);
        messageSender.createFacilityCreatedMessage(email, facility);

    }

    public void addTaskToFacility(Long id, Task task) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        task.setFacility(facility);
        facility.getTasks().add(task);
        facilityRepository.save(facility);
    }

    public void removeFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id)
                        .orElseThrow(NoSuchFacilityException::new);
        facility.getEmployees().forEach(employee -> {
            employee.setWorkplace(null);
        });
        facilityRepository.deleteById(id);
    }

    public void addEmployeeToFacility(String email, long facilityId) {
        employmentRequestService.sendEmploymentRequest(email, facilityId);
    }

    public FacilityDto getFacilityForUser(long id) {
        return mapFacilityToDto(facilityRepository.findByOwnerId(id)
                .orElseThrow(NoSuchFacilityException::new));
    }
}
