package com.elearning.admin.controller;

import com.elearning.model.Resultat;
import com.elearning.service.ResultatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/resultats")
public class AdminResultatMvcController {

    private final ResultatService service;

    public AdminResultatMvcController(ResultatService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        // Si vous ne voulez pas gérer la pagination en admin :
        List<Resultat> results = service.findAll(Pageable.unpaged()).getContent();
        model.addAttribute("resultats", results);
        return "admin/resultat-list";
    }

    /** Formulaire de création */
    @GetMapping("/nouveau")
    public String createForm(Model model) {
        model.addAttribute("resultat", new Resultat());
        return "admin/resultat-form";
    }

    /** Sauvegarde création/édition */
    @PostMapping
    public String save(@ModelAttribute Resultat resultat) {
        service.save(resultat);
        return "redirect:/admin/resultats";
    }

    /** Formulaire d’édition */
    @GetMapping("/{id}/modifier")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("resultat", service.findById(id));
        return "admin/resultat-form";
    }

    /** Suppression */
    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id) {
        service.delete(id);  // ← on appelle delete(id), plus deleteById
        return "redirect:/admin/resultats";
    }
}
