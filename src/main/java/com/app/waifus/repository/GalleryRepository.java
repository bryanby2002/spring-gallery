package com.app.waifus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.waifus.model.Gallery;

@Repository
public interface GalleryRepository  extends JpaRepository<Gallery, Integer>{

  // ---> metodos aqui
  //Metodo abstracto para listar la gallery segun la palabra 
  @Query("SELECT g FROM Gallery g WHERE g.category LIKE %:palabra% or g.etiqueta LIKE %:palabra% or g.img LIKE %:palabra%")
  public List<Gallery> listWord(@Param("palabra") String palabra);
  
  
}


