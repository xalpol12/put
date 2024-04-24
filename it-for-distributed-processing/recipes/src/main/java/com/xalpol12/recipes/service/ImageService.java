package com.xalpol12.recipes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.model.mapper.ImageMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final RecipeRepository recipeRepository;
    private final ImageRepository repository;
    private final ImageMapper mapper;

    public Page<ImageOutput> getAllImageInfos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::imageToOutput);
    }

    public ImageOutput uploadImage(ImageInput fileDetails, MultipartFile file) {
        Image image = mapper.inputToImage(fileDetails, file);
        setName(image, file);
        setRecipe(image, fileDetails);
        Image savedImage = repository.save(image);
        return mapper.imageToOutput(savedImage);
    }

    private void setName(Image image, MultipartFile file) {
        if (image.getName() == null) {
            image.setName(file.getOriginalFilename());
        }
    }

    private void setRecipe(Image image, ImageInput fileDetails) {
        if (fileDetails.getRecipeId() != null) {
            Recipe recipe = findRecipeOrThrow(fileDetails.getRecipeId());
            image.setRecipe(recipe);
        } else {
            throw new EntityNotFoundException("Null recipe id field!"); // TODO: change to null recipe id exception
        }
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
        setRecipe(image, newDetails);
        Image updatedImage = repository.save(image);
        return mapper.imageToOutput(updatedImage);
    }

    public Image getImageData(String uuid) {
        return findImageOrThrow(uuid);
    }

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

    private Recipe findRecipeOrThrow(Long id) {
        return recipeRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + id + " not found."));
    }
}
