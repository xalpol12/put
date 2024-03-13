package com.xalpol12.recipes.controller.iface;

import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Tag(name = "Images API", description = "API for managing images")
@RequestMapping(IImageController.ImagePath.ROOT)
public interface IImageController {

    class ImagePath {
        public static final String ROOT = "/api/images";
        private ImagePath() {}
    }

    @Operation(
            summary = "Display image data",
            description = "Sends the image body for display in web browser as a page. " +
                    "Caches accessed entity for faster retrieval.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the image body for given image ID"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @GetMapping("/{uuid}")
    ResponseEntity<byte[]> getFullImageData(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                            @PathVariable("uuid") String uuid);

    @Operation(
            summary = "Display image thumbnail",
            description = "Generates and sends resized image body. " +
                    "Caches accessed entity for faster retrieval.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the message body for given image ID"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @GetMapping("/{uuid}/thumbnail")
    ResponseEntity<byte[]> getThumbnail(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                        @PathVariable("uuid") String uuid,
                                        @Parameter(name = "width", description = "Width of the thumbnail (in pixels) that " +
                                                "the image should be scaled to, cannot be greater than original image width. " +
                                                "Default value is 100 pixels.")
                                        @RequestParam(required = false, defaultValue = "100") String width,
                                        @Parameter(name = "height", description = "Height of the thumbnail (in pixels) that " +
                                                "the image should be scaled to, cannot be greater than original image height." +
                                                "Default value is 100 pixels.")
                                        @RequestParam(required = false, defaultValue = "100") String height);

    @Operation(
            summary = "Return image info",
            description = "Returns image info in the form of ImageOutput")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available image info"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @GetMapping("/{uuid}/details")
    ResponseEntity<ImageOutput> getImageInfo(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                          @PathVariable("uuid") String uuid);

    @Operation(
            summary = "Return list of all images",
            description = "Returns all images info in the form of ImageOutput list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all available image infos"),
    })
    @GetMapping
    ResponseEntity<List<ImageOutput>> getAllImageInfos();

    @Operation(
            summary = "Upload image",
            description = "Saves new image entity in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully saved new entity in the database"),
            @ApiResponse(responseCode = "400", description = "Couldn't access image data bytes"),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<URI> uploadImage(@Parameter(name = "file details", description = "ImageInput object")
                                    @RequestPart ImageInput fileDetails,
                                    @Parameter(name = "file", description = "MultipartFile object, actual image file")
                                    @RequestPart MultipartFile file);

    @Operation(
            summary = "Delete image",
            description = "Deletes image with given UUID from the database. " +
                    "Also deletes all ScheduledImage entities that were associated with this image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the entity with given UUID from the database"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> deleteImage(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                     @PathVariable("uuid") String uuid);

    @Operation(
            summary = "Delete all images",
            description = "Deletes all images from the database. " +
                    "Also deletes all ScheduledImage entities that were associated with any image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted all Image entities from the database"),
    })
    @DeleteMapping
    ResponseEntity<Void> deleteAllImages();

    @Operation(
            summary = "Update image",
            description = "Replaces old entity with the entity created from provided details, retaining the same ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted entity with given ID in the database"),
            @ApiResponse(responseCode = "400", description = "Couldn't access image data bytes"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @PutMapping(value = ImagePath.ROOT + "/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> updateImage(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                     @PathVariable("uuid") String uuid,
                                     @Parameter(name = "file details", description = "ImageInput object")
                                     @RequestPart ImageInput fileDetails,
                                     @Parameter(name = "file", description = "MultipartFile object, actual image file")
                                     @RequestPart MultipartFile file);

    @Operation(
            summary = "Patch image details",
            description = "Modifies entity with the details provided in the request, " +
                    "null request fields are not processed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted entity with given ID in the database"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @PatchMapping("/{uuid}/details")
    ResponseEntity<Void> updateImageDetails(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                            @PathVariable("uuid") String uuid,
                                            @Parameter(name = "file details", description = "ImageInput object")
                                            @RequestBody ImageInput newDetails);

    @Operation(
            summary = "Patch image data",
            description = "Modifies entity with the image data provided in the request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted entity with given ID in the database"),
            @ApiResponse(responseCode = "400", description = "Couldn't access image data bytes"),
            @ApiResponse(responseCode = "404", description = "Image with given ID not found in the database"),
    })
    @PatchMapping(value = "/{uuid}/data", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> updateImageData(@Parameter(name = "uuid", description = "Unique Image entity identifier")
                                         @PathVariable("uuid") String uuid,
                                         @Parameter(name = "file", description = "MultipartFile object, actual image file")
                                         @RequestPart MultipartFile file);
}
