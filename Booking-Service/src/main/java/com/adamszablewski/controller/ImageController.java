package com.adamszablewski.controller;

import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.model.ImageData;
import com.adamszablewski.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private ImageService imageService;

//    @PostMapping("/")
//    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
//        imageService.uploadImage(file);
//        return ResponseEntity.ok("File upploaded");
//    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName, @RequestHeader("userEmail") String userEmail) throws IOException {
        byte[] data = imageService.dowloadImage(fileName, userEmail);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }
    @GetMapping("/facilityID/{id}")
    public ResponseEntity<?> getImageForFacility(@PathVariable long id, @RequestHeader("userEmail") String userEmail) throws IOException {
        byte[] data = imageService.getImageForFacility(id, userEmail);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }
    @GetMapping("/userId/{id}")
    public ResponseEntity<?> getImageForUser(@PathVariable long id, @RequestHeader("userEmail") String userEmail) throws IOException {
        byte[] data = imageService.getImageForUser(id, userEmail);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }
    @PostMapping("/facilityID/{id}")
    public ResponseEntity<RestResponseDTO<String>> addFacilityImage(@RequestParam("image")MultipartFile file,
                                                                    @PathVariable long id,
                                                                    @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.addFacilityImage(id, file, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
    @PostMapping("/userID/{id}")
    public ResponseEntity<RestResponseDTO<String>> addUserImage(@RequestParam("image")MultipartFile file,
                                                                    @PathVariable long id,
                                                                @RequestHeader("userEmail") String userEmail) throws IOException {
        imageService.addUserImage(id, file, userEmail);
        return ResponseEntity.ok(new RestResponseDTO<>());
    }
}
