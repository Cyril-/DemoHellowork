package com.demo.francetravailscrapper.service;

import com.demo.francetravailscrapper.FranceTravailScrapperApplication;
import com.demo.francetravailscrapper.external.FranceTravailApiClient;
import com.demo.francetravailscrapper.external.models.JobOfferDto;
import com.demo.francetravailscrapper.repository.JobOfferRepository;
import com.demo.francetravailscrapper.repository.models.JobOfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;

@Service
public class JobFetcherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FranceTravailScrapperApplication.class);
    // TODO maybe save data in db instead of file ?
    // TODO if keep file then have path in a Constant
    private static final Path LAST_EXECUTION_DATE_FILE = Paths.get("/opt/app/data/last-execution-date.txt");

    private final FranceTravailApiClient apiClient;
    private final JobOfferRepository jobOfferRepository;

    public JobFetcherService(final FranceTravailApiClient apiClient, final JobOfferRepository jobOfferRepository) {
        this.apiClient = apiClient;
        this.jobOfferRepository = jobOfferRepository;
    }

    // TODO handle updates of offers ?
    // TODO handle duplicate ids ?
    public void fetchAll() {
        Instant now = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant lastExecutionDate = readLastRunDate().truncatedTo(ChronoUnit.SECONDS);

        // TODO make params configurable
        Map<String, String> params = Map.of(
                //TODO store commune INSEE id is constants or properties
                "commune", "35238,33063,75114",
                "minCreationDate", lastExecutionDate.toString(),
                "maxCreationDate", now.toString()
        );

        LOGGER.info("Fetching job offers from {} to {}", lastExecutionDate, now);

        try {
            // TODO do fetch and save to DB by lot
            Collection<JobOfferDto> jobOffers = apiClient.searchOffers(params);
            jobOfferRepository.saveAll(jobOffers.stream().map(JobOfferMapper::toDocument).toList());
            saveCurrentRunDate(now);
        } catch (Exception ex) {
            LOGGER.error("Error while trying to fetch data", ex);
        }
    }

    private Instant readLastRunDate() {
        try {
            if (Files.exists(LAST_EXECUTION_DATE_FILE)) {
                String content = Files.readString(LAST_EXECUTION_DATE_FILE);
                return Instant.parse(content.trim());
            }
        } catch (IOException | DateTimeParseException ex) {
            LOGGER.error("Error while trying to read data in file " + LAST_EXECUTION_DATE_FILE, ex);
        }
        return Instant.now().minus(Duration.ofDays(1));
    }

    private void saveCurrentRunDate(final Instant now) {
        try {
            Files.writeString(LAST_EXECUTION_DATE_FILE, now.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            LOGGER.error("Error while trying to save data in file " + LAST_EXECUTION_DATE_FILE, ex);

        }
    }
}