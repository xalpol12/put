package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.model.mapper.ImageMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;
    private final ImageMapper mapper;
    private final ThumbnailService thumbnailService;

    public Image getImage(String uuid) {
        return repository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Image with id: " + uuid + " not found."));
    }

    public byte[] getImageData(String uuid) {
        Image image = repository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Image with id: " + uuid + " not found."));
        return image.getData();
    }

    public byte[] getThumbnail(String uuid, String width, String height) {
        byte[] imageData = getImageData(uuid);
        return thumbnailService.generateThumbnail(imageData, width, height);
    }

    public ImageOutput getImageInfo(String uuid) {
        Image image = repository.getReferenceById(uuid);
        return mapper.mapToImageOutput(image);
    }

    //TODO: add pagination here
    public List<ImageOutput> getAllImageInfos() {
        return repository
                .findAll()
                .stream()
                .map(mapper::mapToImageOutput)
                .toList();
    }

    public URI uploadImage(ImageInput fileDetails, MultipartFile file) {
        Image image = mapper.mapToImageEntity(fileDetails, file);
        Image savedImage = repository.save(image);
        return URI.create(savedImage.getId());
    }

    public void deleteImage(String uuid) {
        repository.deleteById(uuid);
    }

    public void deleteAllImages() {
        repository.deleteAll();
    }

    //TODO: implement
    public void updateImage(String uuid, ImageInput fileDetails) {
    }

    //TODO: create true PUT, not a fake ass one
}
