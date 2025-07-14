package com.demo.francetravailscrapper.service;

import com.demo.francetravailscrapper.models.JobOffersStatistics;
import com.demo.francetravailscrapper.repository.JobOfferStatsRepository;
import org.springframework.stereotype.Service;

@Service
public class JobOffersStatisticsService {
    private final JobOfferStatsRepository jobOfferStatsRepository;

    public JobOffersStatisticsService(JobOfferStatsRepository jobOfferStatsRepository) {
        this.jobOfferStatsRepository = jobOfferStatsRepository;
    }

    public JobOffersStatistics getStatistics() {
        return new JobOffersStatistics(
                jobOfferStatsRepository.countByTypeContrat(),
                jobOfferStatsRepository.countByEntreprise(),
                jobOfferStatsRepository.countByPays());
    }
}
