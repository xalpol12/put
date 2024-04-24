package com.xalpol12.recipes.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.controller.iface.IImageController;
import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController implements IImageController {
    private final ImageService service;

    @Override
    public ResponseEntity<Page<ImageOutput>> getAllImageInfos(Pageable pageable) {
        return ResponseEntity.ok(service.getAllImageInfos(pageable));
    }

    @Override
    public ResponseEntity<ImageOutput> uploadImage(ImageInput fileDetails, MultipartFile file) {
        ImageOutput output = service.uploadImage(fileDetails, file);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ImagePath.ROOT + "/")
                .path(output.getImageId())
                .build()
                .toUri();

        return ResponseEntity.created(uri)
                .body(output);
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
        Image image = service.getImageData(uuid);

        MediaType mediaType = MediaType.parseMediaType(image.getType());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        return ResponseEntity.ok()
                .headers(headers)
                .body(image.getData());
    }

    @Override
    public ResponseEntity<Void> updateImageData(String uuid, MultipartFile file) throws IOException {
        service.updateImage(uuid, file);
        return ResponseEntity.ok().build();
    }
}
