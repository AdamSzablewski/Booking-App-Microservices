package com.adamszablewski.service;

import com.adamszablewski.exceptions.FIleNotFoundException;
import com.adamszablewski.exceptions.NoSuchFacilityException;
import com.adamszablewski.exceptions.NoSuchUserException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.model.Facility;
import com.adamszablewski.model.ImageData;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.FacilityRepository;
import com.adamszablewski.repository.ImageRepository;
import com.adamszablewski.repository.UserRepository;
import com.adamszablewski.util.ImageUtils;
import com.adamszablewski.util.helpers.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    private ImageData uploadImage(MultipartFile file) throws IOException {
        return imageRepository.save(ImageData.builder()
                .type(file.getContentType())
                .name(file.getOriginalFilename())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public byte[] dowloadImage(String fileName, String userEmail){
        ImageData imageData =  imageRepository.findByName(fileName)
                .orElseThrow(FIleNotFoundException::new);
        return ImageUtils.decompressImage(imageData.getImageData());

    }
    @Transactional
    public void addFacilityImage(long id, MultipartFile file, String userEmail) throws IOException {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(NoSuchFacilityException::new);
        if (!userValidator.isOwner(facility, userEmail)){
            throw new NotAuthorizedException();
        }
        if (facility.getFacilityImage() != null){
            imageRepository.delete(facility.getFacilityImage());
        }
        ImageData image = uploadImage(file);
        facility.setFacilityImage(image);
        facilityRepository.save(facility);
    }

    public byte[] getImageForFacility(long id, String userEmail) {
         Facility facility = facilityRepository.findById(id)
                 .orElseThrow(NoSuchFacilityException::new);
         byte[] imageData = facility.getFacilityImage().getImageData();
         return ImageUtils.decompressImage(imageData);
    }

    public byte[] getImageForUser(long id, String userEmail) {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        byte[] imageData = user.getProfilePhoto().getImageData();
        return ImageUtils.decompressImage(imageData);
    }

    public void addUserImage(long id, MultipartFile file, String userEmail) throws IOException {
        UserClass user = userRepository.findById(id)
                .orElseThrow(NoSuchUserException::new);
        if (!userValidator.isUser(user, userEmail)){
            throw new NotAuthorizedException();
        }
        if (user.getProfilePhoto() != null){
            imageRepository.delete(user.getProfilePhoto());
        }
        ImageData image = uploadImage(file);
        user.setProfilePhoto(image);
        userRepository.save(user);
    }
}
