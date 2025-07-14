package com.demo.francetravailscrapper.models;

import java.util.Map;

public record JobOffersStatistics(Map<String, Integer> countByTypeContrat,
            Map<String, Integer> countByEntreprise,
            Map<String, Integer> countByPays) {}
