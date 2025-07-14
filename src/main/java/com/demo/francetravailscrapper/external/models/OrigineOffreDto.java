package com.demo.francetravailscrapper.external.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrigineOffreDto {
    @JsonProperty("urlOrigine")
    private String urlOrigine;

    public String getUrlOrigine() {
        return urlOrigine;
    }

    @Override
    public String toString() {
        return "OrigineOffreDto{" +
                ", urlOrigine='" + urlOrigine + '\'' +
                '}';
    }
}
