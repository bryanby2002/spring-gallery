package com.app.waifus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.waifus.model.Gallery;

@Repository
public interface GalleryRepository  extends JpaRepository<Gallery, Integer>{

  // ---> metodos aqui

  
}


