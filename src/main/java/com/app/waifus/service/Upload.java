package com.app.waifus.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Upload {
    
    // metodo para cargar las imagenes en un proyecto
    public void upload(@RequestParam("file") MultipartFile file) throws IOException{
       if(!file.isEmpty()){
        String pathImage = "src/main/resources/static/upload/"+file.getOriginalFilename();
        Path pathFile = Paths.get(pathImage);
        Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);

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