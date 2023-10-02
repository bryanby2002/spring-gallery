package com.app.waifus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.waifus.model.Gallery;
import com.app.waifus.repository.GalleryRepository;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepo;

    public Gallery save (Gallery gallery){
        return this.galleryRepo.save(gallery);
    }

    public List<Gallery> ListAll(String palabra){
        if(palabra != null){
            return this.galleryRepo.listWord(palabra);
        }
        return this.galleryRepo.findAll();
    }

    public Gallery update (Gallery gallery){
        return this.save(gallery);
    }

    public Gallery listById(Integer id){
        return this.galleryRepo.findById(id).get();
    }

    public void delete(Integer id){
        this.galleryRepo.deleteById(id);
    }
}
