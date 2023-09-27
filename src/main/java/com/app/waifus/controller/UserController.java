package com.app.waifus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.waifus.model.User;
import com.app.waifus.repository.UserRepository;

@Controller
public class UserController {

  @Autowired
  private UserRepository userRepo;

  @GetMapping("/login")
  public String loginForm() {
    return "login";
  }

  @PostMapping("/login")
  public String login(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      RedirectAttributes redirect,
      Model model) {
    User user = this.userRepo.ValidarUsuario(username, password);
    if (user != null) {
      redirect.addFlashAttribute("alert", "Welcome " + username + " to control image");
      return "redirect:/new";
    } else {
      model.addAttribute("error", "Username and password invalid");
      return "login";
    }
  }

  
}
