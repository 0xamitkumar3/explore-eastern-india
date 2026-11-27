
package com.explore.exploreeasternindia.image.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${app.file.upload-dir}")
    private String uploadDir;

    private static final Set<String> ALLOWED_TYPES =
            Set.of("image/jpeg", "image/png", "image/webp");

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png", ".webp");

    public Resource loadImage(String fileName) throws IOException {

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName).normalize();

        if (!filePath.startsWith(uploadPath) || !Files.exists(filePath)) {
            throw new IOException("Image not found");
        }

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new IOException("Image cannot be read");
        }

        return resource;
    }

    public String storeImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }

        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("Invalid image file");
        }

        String extension = originalName.substring(
                originalName.lastIndexOf(".")
        ).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Only JPG, PNG and WEBP images are allowed"
            );
        }

        String contentType = file.getContentType();

        // Temporary debugging: check MIME type sent by Postman
        System.out.println("Uploaded file content type: " + contentType);

        if (contentType != null) {
            contentType = contentType.toLowerCase().trim();

            if (!ALLOWED_TYPES.contains(contentType)) {
                throw new IllegalArgumentException(
                        "Invalid image content type: " + contentType
                );
            }
        }

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        Files.createDirectories(uploadPath);

        Path targetPath = uploadPath.resolve(fileName).normalize();

        if (!targetPath.startsWith(uploadPath)) {
            throw new IOException("Invalid file path");
        }

        Files.copy(file.getInputStream(), targetPath);

        return fileName;
    }

    public void deleteImage(String fileName) throws IOException {

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName).normalize();

        if (!filePath.startsWith(uploadPath)) {
            throw new IOException("Invalid file path");
        }

        Files.deleteIfExists(filePath);
    }
}