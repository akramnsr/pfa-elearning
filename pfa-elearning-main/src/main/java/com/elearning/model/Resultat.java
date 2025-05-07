package com.elearning.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultat")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float score;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date datePassage;

    private String statut;

    public Resultat() {}

    public Resultat(float score, Date datePassage, String statut) {
        this.score = score;
        this.datePassage = datePassage;
        this.statut = statut;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getDatePassage() {
        return datePassage;
    }

    public void setDatePassage(Date datePassage) {
        this.datePassage = datePassage;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }


    public float calculerScore() {
        return score;
    }

    public boolean estReussi() {
        return score >= 10.0f;
    }

    public String afficherDetails() {
        return "Résultat : " + score + " - " + statut + " le " + datePassage;
    }
}
