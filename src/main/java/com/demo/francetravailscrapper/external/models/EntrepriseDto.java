package com.demo.francetravailscrapper.external.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntrepriseDto {
    @JsonProperty("nom")
    private String nom;

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "EntrepriseDto{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
