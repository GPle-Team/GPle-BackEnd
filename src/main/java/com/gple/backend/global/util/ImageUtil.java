package com.gple.backend.global.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.gple.backend.global.file.ImageMultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class ImageUtil {
    public BufferedImage rotateExifImage(BufferedImage originalImage, Metadata metadata) throws MetadataException {
        int orientation = getOrientation(metadata);

        return switch (orientation) {
            case 3 -> rotate(originalImage, 180);
            case 6 -> rotate(originalImage, 90);
            case 8 -> rotate(originalImage, -90);
            default -> originalImage;
        };
    }

    private BufferedImage makeSquare(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int squareSize = Math.min(width, height);

        int x = (width - squareSize) / 2;
        int y = (height - squareSize) / 2;

        return image.getSubimage(x, y, squareSize, squareSize);
    }

    public MultipartFile resizeMultipartFile(MultipartFile file, String filename) throws IOException, ImageProcessingException, MetadataException {
        Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());

        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        originalImage = rotateExifImage(originalImage, metadata);
        originalImage = makeSquare(originalImage);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", outputStream);

        return imageToMultipartFile(outputStream.toByteArray(), filename);
    }


    private int getOrientation(Metadata metadata) throws MetadataException {
        ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (exif != null) {
            return exif.getInt(ExifDirectoryBase.TAG_ORIENTATION);
        }

        return 1;
    }

    private BufferedImage rotate(BufferedImage image, int angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        g2d.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    private MultipartFile imageToMultipartFile(byte[] imageBytes, String filename){
        return new ImageMultipartFile(
            imageBytes,
            filename,
            "image/jpeg"
        );
    }
}
