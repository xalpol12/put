package com.xalpol12.recipes.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.controller.iface.IImageController;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController implements IImageController {
    private final ImageService service;

    @Override
    public ResponseEntity<List<ImageOutput>> getAllImageInfos() {
        return ResponseEntity.ok(service.getAllImageInfos());
    }

    @Override
    public ResponseEntity<URI> uploadImage(ImageInput fileDetails, MultipartFile file) {
        return ResponseEntity.created(service.uploadImage(fileDetails, file)).build();
    }

    @Override
    public ResponseEntity<ImageOutput> getImageInfo(String uuid) {
        return ResponseEntity.ok(service.getImageInfo(uuid));
    }

    @Override
    public ResponseEntity<Void> deleteImage(String uuid) {
        service.deleteImage(uuid);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ImageOutput> updateImageDetails(String uuid, ImageInput newDetails) {
        return ResponseEntity.ok(service.updateImageDetails(uuid, newDetails));

    }

    @Override
    public ResponseEntity<ImageOutput> patchImageDetails(String uuid, JsonPatch patch) {
        return ResponseEntity.ok(service.patchImageDetails(uuid, patch));
    }

    @Override
    public ResponseEntity<byte[]> getFullImageData(String uuid) {
        return ResponseEntity.ok(service.getImageData(uuid));
    }

    @Override
    public ResponseEntity<Void> updateImageData(String uuid, MultipartFile file) throws IOException {
        service.updateImage(uuid, file);
        return ResponseEntity.ok().build();
    }
}
