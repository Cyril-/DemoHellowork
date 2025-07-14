package com.demo.francetravailscrapper;

import com.demo.francetravailscrapper.models.JobOffersStatistics;
import com.demo.francetravailscrapper.repository.JobOfferStatsRepository;
import com.demo.francetravailscrapper.service.JobFetcherService;
import com.demo.francetravailscrapper.service.JobOffersStatisticsService;
import com.demo.francetravailscrapper.service.StatisticsPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FranceTravailScrapperApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(FranceTravailScrapperApplication.class);

	private final JobFetcherService jobFetcherService;
	private final JobOffersStatisticsService jobOffersStatisticsService;
	private final StatisticsPrinter statisticsPrinter;

	public FranceTravailScrapperApplication(final JobFetcherService jobFetcherService,
											final JobOffersStatisticsService jobOffersStatisticsService,
											final StatisticsPrinter statisticsPrinter) {
		this.jobFetcherService = jobFetcherService;
		this.jobOffersStatisticsService = jobOffersStatisticsService;
		this.statisticsPrinter = statisticsPrinter;
	}

	public static void main(String[] args) {
		SpringApplication.run(FranceTravailScrapperApplication.class, args).close();
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Starting process");

		jobFetcherService.fetchAll();

		// TODO maybe do retrieval of stats by lot if lot of data
		JobOffersStatistics jobOffersStatistics = jobOffersStatisticsService.getStatistics();
		statisticsPrinter.print(jobOffersStatistics);

		LOGGER.info("Ending process");
	}
}
