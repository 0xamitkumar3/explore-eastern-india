package com.explore.exploreeasternindia.image.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.repository.DestinationRepository;
import com.explore.exploreeasternindia.image.entity.DestinationImage;
import com.explore.exploreeasternindia.image.repository.DestinationImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    private final DestinationImageRepository imageRepository;
    private final DestinationRepository destinationRepository;
    private final ImageStorageService imageStorageService;

    public ImageService(
            DestinationImageRepository imageRepository,
            DestinationRepository destinationRepository,
            ImageStorageService imageStorageService) {
        this.imageRepository = imageRepository;
        this.destinationRepository = destinationRepository;
        this.imageStorageService = imageStorageService;
    }


    public Resource getImageFile(Long imageId) throws IOException {

        DestinationImage image = imageRepository.findById(imageId)
                .orElseThrow(() ->
                        new RuntimeException("Image not found"));

        return imageStorageService.loadImage(image.getImagePath());
    }


    @Transactional
    public DestinationImage uploadImage(
            Long destinationId,
            MultipartFile file) throws IOException {

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() ->
                        new RuntimeException("Destination not found"));

        String storedFileName = imageStorageService.storeImage(file);

        DestinationImage image = new DestinationImage();
        image.setImageName(file.getOriginalFilename());
        image.setImagePath(storedFileName);
        image.setDestination(destination);

        try {
            return imageRepository.save(image);
        } catch (RuntimeException exception) {
            imageStorageService.deleteImage(storedFileName);
            throw exception;
        }
    }

    public List<DestinationImage> getImagesByDestination(Long destinationId) {
        if (!destinationRepository.existsById(destinationId)) {
            throw new RuntimeException("Destination not found");
        }

        return imageRepository.findByDestination_Id(destinationId);
    }

    @Transactional
    public void deleteImage(Long imageId) throws IOException {

        DestinationImage image = imageRepository.findById(imageId)
                .orElseThrow(() ->
                        new RuntimeException("Image not found"));

        imageStorageService.deleteImage(image.getImagePath());
        imageRepository.delete(image);
    }
}
