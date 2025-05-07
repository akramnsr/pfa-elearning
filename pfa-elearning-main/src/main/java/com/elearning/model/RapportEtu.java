package com.elearning.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "rapport_etu")
public class RapportEtu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contenu;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateSoumission;

    private String commentaireFormateur;

    public RapportEtu() {}

    public RapportEtu(String contenu, Date dateSoumission, String commentaireFormateur) {
        this.contenu = contenu;
        this.dateSoumission = dateSoumission;
        this.commentaireFormateur = commentaireFormateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateSoumission() {
        return dateSoumission;
    }

    public void setDateSoumission(Date dateSoumission) {
        this.dateSoumission = dateSoumission;
    }

    public String getCommentaireFormateur() {
        return commentaireFormateur;
    }

    public void setCommentaireFormateur(String commentaireFormateur) {
        this.commentaireFormateur = commentaireFormateur;
    }

    public void ajouterContenu(String contenu) {
        this.contenu = contenu;
    }

    public void modifierRapport(String nouveauContenu) {
        this.contenu = nouveauContenu;
    }

    public void ajouterCommentaire(String commentaire) {
        this.commentaireFormateur = commentaire;
    }
}
