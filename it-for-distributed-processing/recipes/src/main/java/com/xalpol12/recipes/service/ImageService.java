package com.xalpol12.recipes.service;

import com.xalpol12.recipes.exception.custom.IncompleteUpdateFormException;
import com.xalpol12.recipes.exception.custom.RecipeNotIncludedException;
import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.model.mapper.ImageMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

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
        if (image.getName() == null) {
            image.setName(file.getOriginalFilename());
        }
        setRecipe(image, fileDetails);
        image.setVersion(0);
        Image savedImage = repository.save(image);
        return mapper.imageToOutput(savedImage);
    }

    private void setRecipe(Image image, ImageInput fileDetails) {
        if (fileDetails.getRecipeId() != null) {
            Recipe recipe = findRecipeOrThrow(fileDetails.getRecipeId());
            image.setRecipe(recipe);
        } else {
            throw new RecipeNotIncludedException("Did not include any recipe id! Image must be associated with a single, existing recipe.");
        }
    }

    public ImageOutput getImageInfo(String uuid) {
        return mapper.imageToOutput(findImageOrThrow(uuid));
    }

    public void deleteImage(String uuid) {
        repository.deleteById(uuid);
    }

    public ImageOutput updateImageDetails(String uuid, ImageInput newDetails, Integer version) {
        Image image = findImageOrThrow(uuid);

        if (!Objects.equals(version, image.getVersion())) {
            throw new OptimisticLockException();
        }

        try {
            image.setName(newDetails.getName());
            image.setVersion(image.getVersion() + 1);
            setRecipe(image, newDetails);
            return mapper.imageToOutput(repository.save(image));
        } catch (NullPointerException e) {
            throw new IncompleteUpdateFormException("Update form does not include all the entity's fields");
        }
    }

    public ImageOutput patchImageDetails(String uuid, ImageInput newDetails) {
        Image image = findImageOrThrow(uuid);

        if (newDetails.getName() != null) {
            image.setName(newDetails.getName());
        }
        if (newDetails.getRecipeId() != null) {
            Recipe recipe = findRecipeOrThrow(newDetails.getRecipeId());
            image.setRecipe(recipe);
        }

        return mapper.imageToOutput(repository.save(image));
    }

    public Image getImageData(String uuid) {
        return findImageOrThrow(uuid);
    }

    public void updateImage(String uuid, MultipartFile file, Integer version) {
        Image image = findImageOrThrow(uuid);

        if (!Objects.equals(version, image.getVersion())) {
            throw new OptimisticLockException();
        }

        try {
            image.setData(file.getBytes());
            image.setType(file.getContentType());
            image.setVersion(image.getVersion() + 1);
            repository.save(image);
        } catch (Exception e) {
            throw new IncompleteUpdateFormException("Update form does not include all the entity's fields");
        }
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
