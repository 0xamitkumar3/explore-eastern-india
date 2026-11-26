package com.explore.exploreeasternindia.image.dto;

import java.time.LocalDateTime;

public record DestinationImageResponse(
        Long id,
        String imageName,
        String imagePath,
        LocalDateTime uploadedAt
) {}
