package com.adamszablewski.service;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.dto.FacilityDto;
import com.adamszablewski.exceptions.NoSuchAppointmentException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.model.*;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.repository.PortfolioRepository;
import com.adamszablewski.util.UserTools;
import com.adamszablewski.exceptions.FacilityNameTakenException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.util.UserValidator;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.messages.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Set;

import static com.adamszablewski.dto.mapper.Mapper.mapFacilityToDto;

@Service
@AllArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final EmploymentRequestService employmentRequestService;
    private final MessageSender messageSender;
    private final UserTools userTools;
    private final UserValidator userValidator;
    private final AppointmentRepository appointmentRepository;
    private final Dao dao;
    private final PortfolioRepository portfolioRepository;

    public Set<FacilityDto> getAllFacilities() {
        return mapFacilityToDto((Set<Facility>) facilityRepository.findAll());
    }

    public Set<FacilityDto> getAllFacilitiesForRegion(String region) {
        return mapFacilityToDto(facilityRepository.findByRegion(region), true);

    }

    public Set<FacilityDto> getAllFacilitiesForCity(String city) {
        return mapFacilityToDto(facilityRepository.findByCity(city));
    }
    @Transactional
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
                .portfolio(Portfolio.builder()
                        .imageIds(new ArrayList<>())
                        .build())
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

    public void addTaskToFacility(Long id, Task task, String userEmail) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        if (!userValidator.isOwner(facility, userEmail)){
            throw new NotAuthorizedException("Not authorized for this action");
        }
        task.setFacility(facility);
        facility.getTasks().add(task);
        facilityRepository.save(facility);
    }

    public void removeFacilityById(Long id, String userEmail) {
        Facility facility = facilityRepository.findById(id)
                        .orElseThrow(NoSuchFacilityException::new);
        if(!userValidator.isOwner(facility, userEmail)){
            throw new NotAuthorizedException("Not authorized for this action");
        }
        dao.deleteFacility(facility);
    }

    public void addEmployeeToFacility(String email, long facilityId, String userEmail) {
        Facility facility = facilityRepository.findById(facilityId)
                        .orElseThrow(NoSuchFacilityException::new);
        if (!userValidator.isOwner(facility, userEmail)){
            throw new NotAuthorizedException("Not authorized for this action");
        }
        employmentRequestService.sendEmploymentRequest(email, facilityId);
    }

    public FacilityDto getFacilityForUser(long id) {
        return mapFacilityToDto(facilityRepository.findByOwnerId(id)
                .orElseThrow(NoSuchFacilityException::new));
    }

    public String getOwnerMailByFacilityID(long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        return facility.getOwner().getUser().getEmail();
    }

    public Long getFacilityIdByAppointmentId(long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(NoSuchAppointmentException::new);
        return appointment.getFacility().getId();
    }

    public void addImageToFacilityPortfolio(Long facilityId, String imageId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(NoSuchFacilityException::new);
        Portfolio portfolio = facility.getPortfolio();
        portfolio.getImageIds().add(imageId);

        portfolioRepository.save(portfolio);
    }
}
