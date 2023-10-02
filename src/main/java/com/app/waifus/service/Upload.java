package com.app.waifus.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.waifus.model.Gallery;

@Service
public class Upload {

    public String getExtension(MultipartFile file) {
        // Obtiene el nombre original del archivo desde el objeto MultipartFile
        String originalName = file.getOriginalFilename();  
        // Verifica si el nombre original del archivo no es nulo
        if (originalName != null) {
            // Busca la posición del último punto en el nombre original del archivo
            int lastIndex = originalName.lastIndexOf(".");
            
            // Verifica si se encontró al menos un punto en el nombre del archivo
            if (lastIndex != -1) {
                // Retorna la parte del nombre del archivo que incluye el punto y la extensión
                return originalName.substring(lastIndex);
            }
        }
        // Si no se pudo obtener la extensión con punto, retorna null
        return null;
    }
    

    public String uniqueFileName (MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String uniqueFileName = uuid.toString().replace("-", "");
        String extension = getExtension(file);
        String fileNameFinal = uniqueFileName+extension;
        return fileNameFinal;
    }
    
    // metodo para cargar las imagenes en un proyecto
    public void upload(MultipartFile file, Gallery gallery) throws IOException{
        String uniqueFileName = uniqueFileName(file);
       if(!file.isEmpty()){
        String pathImage = "src/main/resources/static/upload/"+uniqueFileName;
        Path pathFile = Paths.get(pathImage);
        Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
        gallery.setImg(uniqueFileName);
       }
    }
    
    public void delete(@RequestParam("file") MultipartFile file) throws IOException{
        if(!file.isEmpty()){
            String pathImage = "src/main/resources/static/upload/"+file.getOriginalFilename();
            Path pathFile = Paths.get(pathImage);
            if(Files.exists(pathFile)){
                Files.deleteIfExists(pathFile);
            }
        }
    }
}