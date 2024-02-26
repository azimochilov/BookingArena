package com.booking.service.filesystem;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

@Service
@Configuration
@Getter
@Setter
@ConfigurationProperties("app.image")
public class ImageService {
    private String bucket;

    @SneakyThrows
    public void upload(String fileName, InputStream content) {
        Path fullImagePath = Path.of(bucket, UUID.randomUUID()+fileName);

        try (content){
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String fileName) {
        Path fullImagePath = Path.of(bucket, fileName);
        return Files.exists(fullImagePath) ? Optional.of(Files.readAllBytes(fullImagePath)) : Optional.empty();
    }
}
