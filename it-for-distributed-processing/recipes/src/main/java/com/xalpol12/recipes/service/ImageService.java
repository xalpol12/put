package com.xalpol12.recipes.service;

import com.github.fge.jsonpatch.JsonPatch;
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

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;
    private final ImageMapper mapper;

    //TODO: add pagination here
    public List<ImageOutput> getAllImageInfos() {
        return mapper.imageToOutput(repository.findAll());
    }

    public URI uploadImage(ImageInput fileDetails, MultipartFile file) {
        Image image = mapper.inputToImage(fileDetails, file);
        image.setCreatedAt(LocalDateTime.now());
        image.setModifiedAt(LocalDateTime.now());
        Image savedImage = repository.save(image);
        return URI.create(savedImage.getId());
    }

    public ImageOutput getImageInfo(String uuid) {
        Image image = repository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Image with given id does not exist"));
        return mapper.imageToOutput(image);
    }

    public void deleteImage(String uuid) {
        repository.deleteById(uuid);
    }

    //TODO: implement
    public ImageOutput updateImageDetails(String uuid, ImageInput newDetails) {

        return null;
    }

    //TODO: implement
    public ImageOutput patchImageDetails(String uuid, JsonPatch patch) {
        return null;
    }

    public Image getImageData(String uuid) {
        return repository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Image with id: " + uuid + " not found."));
    }

    //TODO: implement
    public void updateImage(String uuid, MultipartFile file) throws IOException {
        Image image = repository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Could not find image with given id"));
        image.setData(file.getBytes());
    }
}
