package com.demo.francetravailscrapper.external.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOfferResponseDto {
    @JsonProperty("resultats")
    private List<JobOfferDto> resultats;

    public List<JobOfferDto> getResultats() {
        return resultats;
    }

    @Override
    public String toString() {
        return "JobOfferResponseDto{" +
                "resultats=" + resultats +
                '}';
    }
}
