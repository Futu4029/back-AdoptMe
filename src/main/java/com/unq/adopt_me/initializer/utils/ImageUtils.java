package com.unq.adopt_me.initializer.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageUtils {

    // Método para cargar una imagen desde un archivo y convertirla a una cadena base64
    public static String imageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        byte[] imageBytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}