package com.demo.francetravailscrapper.external.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOfferDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("intitule")
    private String intitule;
    @JsonProperty("description")
    private String description;
    @JsonProperty("dateCreation")
    private String dateCreation;
    @JsonProperty("dateActualisation")
    private String dateActualisation;
    @JsonProperty("entreprise")
    private EntrepriseDto entreprise;
    @JsonProperty("typeContrat")
    private String typeContrat;
    @JsonProperty("origineOffre")
    private OrigineOffreDto origineOffre;
    @JsonProperty("paysContinent")
    private String paysContinent;

    public String getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getDateActualisation() {
        return dateActualisation;
    }

    public EntrepriseDto getEntreprise() {
        return entreprise;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public OrigineOffreDto getOrigineOffre() {
        return origineOffre;
    }

    public String getPaysContinent() {
        return paysContinent;
    }

    @Override
    public String toString() {
        return "JobOfferDto{" +
                "id='" + id + '\'' +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", dateCreation='" + dateCreation + '\'' +
                ", dateActualisation='" + dateActualisation + '\'' +
                ", entreprise=" + entreprise +
                ", typeContrat='" + typeContrat + '\'' +
                ", origineOffre=" + origineOffre +
                ", paysContinent='" + paysContinent + '\'' +
                '}';
    }
}
