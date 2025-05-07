package com.elearning.admin.controller;

import com.elearning.model.RapportEtu;
import com.elearning.repository.RapportEtuRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/rapports-etu")
public class AdminRapportEtuMvcController {

    private final RapportEtuRepository repo;

    public AdminRapportEtuMvcController(RapportEtuRepository repo) {
        this.repo = repo;
    }

    // 1. Liste
    @GetMapping
    public String list(Model model) {
        model.addAttribute("rapports", repo.findAll());
        return "/admin/rapports-etu-list";
    }

    // 2. Formulaire « nouveau »
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("rapportEtu", new RapportEtu());
        return "/admin/rapports-etu-form";
    }

    // 3. Formulaire « édition »
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        RapportEtu r = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide: " + id));
        model.addAttribute("rapportEtu", r);
        return "/admin/rapports-etu-form";
    }

    // 4. Création
    @PostMapping
    public String create(@ModelAttribute RapportEtu rapportEtu) {
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }

    // 5. Mise à jour
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute RapportEtu rapportEtu) {
        rapportEtu.setId(Math.toIntExact(id));
        repo.save(rapportEtu);
        return "redirect:/admin/rapports-etu";
    }
    // Supprime la formation après confirmation
    @PostMapping("/{id}/supprimer")
    public String supprimerRapport(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/rapports-etu";
    }
}
