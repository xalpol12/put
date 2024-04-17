package com.xalpol12.recipes.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.model.mapper.ImageMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;
    private final ImageMapper mapper;

    public Page<ImageOutput> getAllImageInfos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::imageToOutput);
    }

    public URI uploadImage(ImageInput fileDetails, MultipartFile file) {
        Image image = mapper.inputToImage(fileDetails, file);
        Image savedImage = repository.save(image);
        String imageId = savedImage.getId();
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(imageId)
                .build()
                .toUri();
    }

    public ImageOutput getImageInfo(String uuid) {
        return mapper.imageToOutput(findImageOrThrow(uuid));
    }

    public void deleteImage(String uuid) {
        repository.deleteById(uuid);
    }

    public ImageOutput updateImageDetails(String uuid, ImageInput newDetails) {
        Image image = findImageOrThrow(uuid);
        if (newDetails.getName() != null) {
            image.setName(newDetails.getName());
        }
        Image updatedImage = repository.save(image);
        return mapper.imageToOutput(updatedImage);
    }

    public ImageOutput patchImageDetails(String uuid, JsonPatch patch) {
        Image image = findImageOrThrow(uuid);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(image, JsonNode.class));
            Image patchedImage = objectMapper.treeToValue(patched, Image.class);

            if (patchedImage.getName() != null) {
                image.setName(patchedImage.getName());
            }
            if (patchedImage.getRecipes() != null) {
                image.setRecipes(patchedImage.getRecipes());
            }

            image.setModifiedAt(LocalDateTime.now());
            Image updatedImage = repository.save(image);

            return mapper.imageToOutput(updatedImage);
        } catch (Exception e) { //TODO: add exception handling
            e.printStackTrace();
            return null;
        }
    }

    public Image getImageData(String uuid) {
        return findImageOrThrow(uuid);
    }

    //TODO: implement
    public void updateImage(String uuid, MultipartFile file) throws IOException {
        Image image = findImageOrThrow(uuid);
        image.setData(file.getBytes());
        image.setType(file.getContentType());
        repository.save(image);
    }

    private Image findImageOrThrow(String uuid) {
        return repository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Image with id: " + uuid + " not found."));

    }
}
