package com.app.waifus.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.waifus.model.Gallery;

@Service
public class Upload {

    private static final String UPLOAD_DIR = "src/main/resources/static/upload/";

    public String getExtension(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName != null) {
            int lastIndex = originalName.lastIndexOf(".");
            if (lastIndex != -1) {
                return originalName.substring(lastIndex);
            }
        }
        return null;
    }

    public String uniqueFileName(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String uniqueFileName = uuid.toString().replace("-", "");
        String extension = getExtension(file);
        String fileNameFinal = uniqueFileName + extension;
        return fileNameFinal;
    }

    // metodo para cargar las imagenes en un proyecto
    public void upload(MultipartFile file, Gallery gallery) throws IOException {
        String uniqueFileName = uniqueFileName(file);
        if (!file.isEmpty()) {
            String pathImage = UPLOAD_DIR + uniqueFileName;
            Path pathFile = Paths.get(pathImage);
            Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
            gallery.setImg(uniqueFileName);
        }
    }

    public void update(String fileName, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Path pathFile = Paths.get(UPLOAD_DIR,fileName);
            if(Files.exists(pathFile)){
                Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public void deleteFile(String fileName) throws IOException {
        Path pathFile = Paths.get(UPLOAD_DIR, fileName);
        if (Files.exists(pathFile)) {
            Files.delete(pathFile);
        }
    }
}