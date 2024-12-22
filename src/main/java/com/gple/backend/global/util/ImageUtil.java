package com.gple.backend.global.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.gple.backend.global.file.ImageMultipartFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@Component
public class ImageUtil {
    public BufferedImage rotateExifImage(BufferedImage originalImage, Metadata metadata) throws MetadataException, IOException, ImageProcessingException {
        int orientation = getOrientation(metadata);

        return switch (orientation) {
            case 3 -> rotate(originalImage, 180);
            case 6 -> rotate(originalImage, 90);
            case 8 -> rotate(originalImage, -90);
            default -> originalImage;
        };
    }

    public MultipartFile resizeMultipartFile(MultipartFile file, String filename) throws IOException, ImageProcessingException, MetadataException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());

        originalImage = rotateExifImage(originalImage, metadata);

        int originWidth = originalImage.getWidth();
        int originHeight = originalImage.getHeight();

        int newWidth, newHeight;
        if (originWidth > originHeight) {
            newWidth = 390;
            newHeight = (originHeight * 390) / originWidth;
        } else {
            newHeight = 390;
            newWidth = (originWidth * 390) / originHeight;
        }

        Image tmpImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmpImage, 0, 0, null);
        g2d.dispose();

        BufferedImage outputImage = new BufferedImage(390, 390, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = outputImage.createGraphics();
        g.setColor(new Color(0x263034));
        g.fillRect(0, 0, 390, 390);

        int x = (390 - newWidth) / 2;
        int y = (390 - newHeight) / 2;
        g.drawImage(resizedImage, x, y, null);
        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", outputStream);

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

        g2d.rotate(Math.toRadians(angle), (double) width / 2, (double) height / 2);
        g2d.drawImage(image, null, 0, 0);
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
