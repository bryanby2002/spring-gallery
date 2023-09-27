package com.app.waifus.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
            this.uploadImage.upload(file);
            String fileName = file.getOriginalFilename();
            gallery.setImg(fileName);
            this.galleryServ.save(gallery);
            redirect.addFlashAttribute("mensaje", "Saved image");

        } catch (IOException e) {
            redirect.addFlashAttribute("error", "Error al subir la imagen");
        }
        return "redirect:/new";
    }
}
