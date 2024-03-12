package com.xalpol12.recipes.controller;

import com.xalpol12.recipes.controller.iface.IImageController;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController implements IImageController {

    private final ImageService service;

    @Override
    public ResponseEntity<byte[]> getFullImageData(String uuid) {
        byte[] data = service.getImageData(uuid);
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<byte[]> getThumbnail(String uuid, String width, String height) {
        byte[] data = service.getThumbnail(uuid, width, height);
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<ImageOutput> getImageInfo(String uuid) {
        ImageOutput output = service.getImageInfo(uuid);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<List<ImageOutput>> getAllImageInfos() {
        List<ImageOutput> outputs = service.getAllImageInfos();
        return ResponseEntity.ok(outputs);
    }

    @Override
    public ResponseEntity<URI> uploadImage(ImageInput fileDetails, MultipartFile file) {
        URI location = service.uploadImage(fileDetails, file);
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<Void> deleteImage(String uuid) {
        service.deleteImage(uuid);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAllImages() {
        service.deleteAllImages();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateImage(String uuid, ImageInput fileDetails, MultipartFile file) {
        throw new NotImplementedException("Endpoint not implemented yet");
    }

    @Override
    public ResponseEntity<Void> updateImageDetails(String uuid, ImageInput newDetails) {
        throw new NotImplementedException("Endpoint not implemented yet");
    }

    @Override
    public ResponseEntity<Void> updateImageData(String uuid, MultipartFile file) {
        throw new NotImplementedException("Endpoint not implemented yet");
    }
}
