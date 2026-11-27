package com.explore.exploreeasternindia.image.controller;

import com.explore.exploreeasternindia.image.dto.DestinationImageResponse;
import com.explore.exploreeasternindia.image.entity.DestinationImage;
import com.explore.exploreeasternindia.image.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaTypeFactory;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(
            value = "/{destinationId}/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<DestinationImageResponse> uploadImage(
            @PathVariable Long destinationId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        DestinationImage image =
                imageService.uploadImage(destinationId, file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(image));
    }

    @GetMapping("/{destinationId}/images")
    public ResponseEntity<List<DestinationImageResponse>> getImages(
            @PathVariable Long destinationId) {

        List<DestinationImageResponse> images =
                imageService.getImagesByDestination(destinationId)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(images);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(
            @PathVariable Long imageId) throws IOException {

        imageService.deleteImage(imageId);

        return ResponseEntity.ok("Image deleted successfully!");
    }


    @GetMapping("/images/{imageId}/file")
    public ResponseEntity<Resource> getImageFile(
            @PathVariable Long imageId) throws IOException {

        Resource resource = imageService.getImageFile(imageId);

        String contentType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM)
                .toString();

        return ResponseEntity.ok()
                .contentType(
                        org.springframework.http.MediaType.parseMediaType(contentType)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\""
                )
                .body(resource);
    }


    private DestinationImageResponse toResponse(DestinationImage image) {
        return new DestinationImageResponse(
                image.getId(),
                image.getImageName(),
                image.getImagePath(),
                image.getUploadedAt()
        );
    }
}
