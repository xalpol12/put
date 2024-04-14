package com.xalpol12.recipes.controller;

import com.github.fge.jsonpatch.JsonPatch;
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
    public ResponseEntity<List<ImageOutput>> getAllImageInfos() {
        return null;
    }

    @Override
    public ResponseEntity<URI> uploadImage(ImageInput fileDetails, MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<ImageOutput> getImageInfo(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteImage(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<ImageOutput> updateImageDetails(String uuid, ImageInput newDetails) {
        return null;
    }

    @Override
    public ResponseEntity<Void> patchImageDetails(String uuid, JsonPatch patch) {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getFullImageData(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateImageData(String uuid, MultipartFile file) {
        return null;
    }
}
