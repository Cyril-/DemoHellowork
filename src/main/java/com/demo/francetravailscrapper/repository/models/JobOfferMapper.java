package com.demo.francetravailscrapper.repository.models;

import com.demo.francetravailscrapper.external.models.JobOfferDto;

public class JobOfferMapper {

    public static JobOfferDocument toDocument(JobOfferDto dto) {
        return new JobOfferDocument(
                dto.getId(),
                dto.getIntitule(),
                dto.getDescription(),
                dto.getTypeContrat(),
                dto.getEntreprise().getNom(),
                dto.getPaysContinent(),
                dto.getOrigineOffre().getUrlOrigine()
        );
    }
}