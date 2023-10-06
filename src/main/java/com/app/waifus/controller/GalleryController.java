package com.app.waifus.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.waifus.model.Gallery;
import com.app.waifus.service.GalleryService;
import com.app.waifus.service.Upload;

@Controller
public class GalleryController {

    @Autowired
    private Upload uploadImage;

    @Autowired
    private GalleryService galleryServ;

    @GetMapping("/")
    public String galleryList(Model model, @Param("palabra") String palabra) {
        model.addAttribute("gallerys", this.galleryServ.ListAll(palabra));
        model.addAttribute("palabra", palabra);
        return "index";
    }

    @GetMapping("/control")
    public String control(Model model, @Param("palabra") String palabra) {
        model.addAttribute("gallerys", this.galleryServ.ListAll(palabra));
        model.addAttribute("palabra", palabra);
        return "control";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("gallery", new Gallery());
        return "new";
    }

    @PostMapping("/new-image")
    public String save(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("gallery") Gallery gallery,
            RedirectAttributes redirect) {
        try {
            this.uploadImage.upload(file, gallery);
            this.galleryServ.save(gallery);
            redirect.addFlashAttribute("mensaje", "Saved image");

        } catch (IOException e) {
            e.printStackTrace();
            redirect.addFlashAttribute("error", "Error al subir la imagen");
        }
        return "redirect:/new";
    }

    @GetMapping("/{id}")
    public String editForm(Model model, @PathVariable Integer id) {
        model.addAttribute("gallery", this.galleryServ.listById(id));
        return "editGallery";
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Integer id,
            @ModelAttribute("gallery") Gallery gallery,
            @RequestParam("file") MultipartFile file,
            Model model,
            RedirectAttributes redirect) {
        try {
            if (file.isEmpty()) {
                redirect.addFlashAttribute("mensaje", "No existe el archivo");
            }
            Gallery galleryName = this.galleryServ.listById(id);
            String fileName = galleryName.getImg();
            if (fileName != null) {
                Gallery exist = new Gallery();
                exist.setId(id);
                exist.setEtiqueta(gallery.getEtiqueta());
                exist.setCategory(gallery.getCategory());
                exist.setImg(fileName);
                this.uploadImage.update(fileName, file);
                this.galleryServ.save(exist);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/control";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        try {
            Gallery gallery = this.galleryServ.listById(id);
            String fileName = gallery.getImg();
            if (fileName != null) {
                this.uploadImage.deleteFile(fileName);
            }
            this.galleryServ.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/control";
    }
}