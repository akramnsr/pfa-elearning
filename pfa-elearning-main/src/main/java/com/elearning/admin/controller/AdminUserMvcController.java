package com.elearning.admin.controller;

import com.elearning.model.User;
import com.elearning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserMvcController {

    private final UserService service;

    public AdminUserMvcController(UserService service) {
        this.service = service;
    }

    /** LISTE DES UTILISATEURS */
    @GetMapping
    public String list(Model model) {
        // récupère tout sans pagination
        model.addAttribute("users", service.findAll(Pageable.unpaged()).getContent());
        return "admin/user-list";
    }

    /** FORMULAIRE DE CRÉATION */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user-form";
    }

    /** SAUVEGARDE CRÉATION / ÉDITION */
    @PostMapping
    public String save(
            @ModelAttribute("user") @Valid User user,
            BindingResult binding,
            Model model
    ) {
        if (binding.hasErrors()) {
            // on renvoie le formulaire s’il y a des erreurs
            return "admin/user-form";
        }
        service.save(user);
        // redirige vers la liste pour voir le nouvel utilisateur
        return "redirect:/admin/users";
    }

    /** FORMULAIRE D’ÉDITION */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", service.findById(id));
        return "admin/user-form";
    }

    /** SUPPRESSION */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/users";
    }
}
