package com.demo.francetravailscrapper.repository.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "job_offers")
public class JobOfferDocument {

    @Id
    private String id;

    // TODO add more data if needed
    // TODO normalize name between english and french in different classes
    private String intitule;
    private String description;
    private String typeContrat;
    private String entreprise;
    private String pays;
    private String url;

    public JobOfferDocument() {}

    public JobOfferDocument(final String id, final String intitule, final String description,
                            final String typeContrat, final String entreprise, final String pays, final String url) {
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.typeContrat = typeContrat;
        this.entreprise = entreprise;
        this.pays = pays;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public String getPays() {
        return pays;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "JobOfferDocument{" +
                "id='" + id + '\'' +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", entreprise='" + entreprise + '\'' +
                ", pays='" + pays + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
