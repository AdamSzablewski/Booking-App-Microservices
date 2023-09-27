package com.adamszablewski.facilities.service;

import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.employmentRequests.util.UserTools;
import com.adamszablewski.exceptions.FacilityNameTakenException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.facilities.Facility;
import com.adamszablewski.employmentRequests.EmploymentRequestService;
import com.adamszablewski.facilities.repository.FacilityRepository;
import com.adamszablewski.feignClients.UserServiceClient;
import com.adamszablewski.feignClients.classes.Owner;
import com.adamszablewski.feignClients.classes.UserClass;
import com.adamszablewski.messages.MessageSender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import com.adamszablewski.tasks.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

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
    public List<FacilityDto> getAllFacilities() {
        System.out.println(mapFacilityToDto(facilityRepository.findAll()));
        return mapFacilityToDto(facilityRepository.findAll());
    }

    public List<FacilityDto> getAllFacilitiesForRegion(String region) {
        return mapFacilityToDto(facilityRepository.findByRegion(region));
    }

    public List<FacilityDto> getAllFacilitiesForCity(String city) {
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
        System.out.println(owner);
        if(owner.getFacilities() != null){
            owner.getFacilities().add(newFacility);
        }
        else {
            owner.setFacilities(List.of(newFacility));
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
