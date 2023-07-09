package com.trip.server.service;

import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;

import com.trip.server.database.entity.Image;
import com.trip.server.database.repository.ImageRepository;
import com.trip.server.exception.InternalServerErrorException;
import com.trip.server.exception.NotFoundException;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image getById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(ImageService::getNotFoundException);
    }

    public Image save(byte[] content) {
        var image = Image.builder()
                .content(content)
                .build();

        return imageRepository.save(image);
    }

    public BufferedImage getBufferedImageById(Long id) {
        var image = getById(id);

        try (var byteStream = new ByteArrayInputStream(image.getContent())) {
            var bufferedImage = ImageIO.read(byteStream);
            return Optional.ofNullable(bufferedImage)
                    .orElseThrow(() -> new InternalServerErrorException("Формат изображения не распознан"));
        } catch (IOException e) {
            throw new InternalServerErrorException("Не удалось конвертировать маccив байтов в изображение", e);
        }
    }

    private static NotFoundException getNotFoundException() {
        return new NotFoundException("Изображение не найдено");
    }

}
