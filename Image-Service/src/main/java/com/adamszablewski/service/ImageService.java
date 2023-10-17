package com.adamszablewski.service;


import com.adamszablewski.exceptions.FIleNotFoundException;
import com.adamszablewski.exceptions.NotAuthorizedException;
import com.adamszablewski.model.FacilityPhoto;
import com.adamszablewski.model.ImageData;
import com.adamszablewski.model.ProfilePhoto;
import com.adamszablewski.repository.FacilityPhotoRepository;
import com.adamszablewski.repository.ImageRepository;
import com.adamszablewski.repository.ProfilePhotoRepository;
import com.adamszablewski.util.ImageUtils;
import com.adamszablewski.util.UserValidator;
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
    private final ProfilePhotoRepository profilePhotoRepository;
    private final FacilityPhotoRepository facilityPhotoRepository;

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

    public void addFacilityImage(long id, MultipartFile file, String userEmail, long userId) throws IOException {

        if (!userValidator.isOwner(id, userEmail)){
            throw new NotAuthorizedException();
        }
        ImageData image = uploadImage(file);
        FacilityPhoto facilityPhoto = FacilityPhoto.builder()
                .facilityId(id)
                .userId(userId)
                .image(image)
                .build();
        facilityPhotoRepository.save(facilityPhoto);
    }

    public byte[] getImageForFacility(long id, String userEmail) {
         FacilityPhoto facilityPhoto = facilityPhotoRepository.findById(id)
                 .orElseThrow(FIleNotFoundException::new);
         byte[] imageData = facilityPhoto.getImage().getImageData();
         return ImageUtils.decompressImage(imageData);
    }
    @Transactional
    public byte[] getImageForUser(long id, String userEmail) {
        ProfilePhoto profilePhoto = profilePhotoRepository.findByUserId(id)
                .orElseThrow(FIleNotFoundException::new);
        byte[] imageData = profilePhoto.getImage().getImageData();
        return ImageUtils.decompressImage(imageData);
    }

    public void addUserImage(long id, MultipartFile file, String userEmail) throws IOException {

        if (!userValidator.isUser(id, userEmail)){
            throw new NotAuthorizedException();
        }
        profilePhotoRepository.deleteByUserId(id);
        ImageData image = uploadImage(file);
        ProfilePhoto profilePhoto = ProfilePhoto.builder()
                .userId(id)
                .image(image)
                .build();
        profilePhotoRepository.save(profilePhoto);
    }

    public void deleteImagesForUser(long userId) {
        facilityPhotoRepository.deleteAllByUserId(userId);
        profilePhotoRepository.deleteByUserId(userId);
    }
}
