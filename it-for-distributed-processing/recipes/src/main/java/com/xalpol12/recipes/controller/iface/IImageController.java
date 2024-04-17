package com.xalpol12.recipes.controller.iface;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Tag(name = "Images API", description = "API for managing images")
public interface IImageController {

    class ImagePath {
        public static final String ROOT = "/api/v1/images";
        private ImagePath() {}
    }

    @GetMapping(ImagePath.ROOT)
    ResponseEntity<Page<ImageOutput>> getAllImageInfos(Pageable pageable);

    @PostMapping(path = ImagePath.ROOT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ImageOutput> uploadImage(@RequestPart ImageInput fileDetails,
                                    @RequestPart MultipartFile file);

    @GetMapping(ImagePath.ROOT + "/{uuid}")
    ResponseEntity<ImageOutput> getImageInfo(@PathVariable("uuid") String uuid);

    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> deleteImage(@PathVariable("uuid") String uuid);

    @PutMapping(ImagePath.ROOT + "/{uuid}")
    ResponseEntity<ImageOutput> updateImageDetails(@PathVariable("uuid") String uuid,
                                                   @RequestBody ImageInput newDetails);

    @PatchMapping(ImagePath.ROOT + "/{uuid}")
    ResponseEntity<ImageOutput> patchImageDetails(@PathVariable("uuid") String uuid,
                                           @RequestBody JsonPatch patch);

    @GetMapping(ImagePath.ROOT + "/{uuid}/raw")
    ResponseEntity<byte[]> getFullImageData(@PathVariable("uuid") String uuid);

    @PutMapping(value = ImagePath.ROOT + "/{uuid}/raw", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> updateImageData(@PathVariable("uuid") String uuid,
                                         @RequestPart MultipartFile file) throws IOException;
}
