package com.demo.francetravailscrapper.service;

import com.demo.francetravailscrapper.models.JobOffersStatistics;
import org.springframework.stereotype.Service;

@Service
public class StatisticsPrinter {

    // TODO export data in file
    public void print(JobOffersStatistics stats) {
        System.out.println("/*********************************************/");
        System.out.println("/********** Statistiques des offres **********/");
        System.out.println("/*********************************************/");

        System.out.println("\n==== Par contrat ===");
        stats.countByTypeContrat().forEach((type, count) ->
                System.out.println(type + ": " + count));

        System.out.println("\n==== Par entreprise ===");
        stats.countByEntreprise().forEach((entreprise, count) ->
                System.out.println(entreprise + ": " + count));

        // TODO le pays est toujours vide. Comment le déduire ?
        System.out.println("\n==== Par pays ===");
        stats.countByPays().forEach((pays, count) ->
                System.out.println(pays + ": " + count));
    }
}
