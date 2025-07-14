package com.demo.francetravailscrapper.external;

import com.demo.francetravailscrapper.external.auth.AuthInterceptor;
import com.demo.francetravailscrapper.external.models.JobOfferDto;
import com.demo.francetravailscrapper.external.models.JobOfferResponseDto;
import com.demo.francetravailscrapper.external.utils.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Component
public class FranceTravailApiClient {

    private static final int MAX_ELEMENT_PER_PAGE = 150;
    private static final int MAX_REQUEST_PER_SECOND = 10;
    private static final int MAX_RANGE_START_VALUE = 3000;

    private final RestTemplate restTemplate;
    private final String baseUrl;
    // TODO if API calls from different API are needed, do multiple clients ? Or a Map<Scope, RateLimiter> ?
    private final RateLimiter rateLimiter;

    public FranceTravailApiClient(final RestTemplateBuilder builder,
                                  @Value("${francetravail.api.base-url}") String baseUrl,
                                  final AuthInterceptor authInterceptor) {
        this.restTemplate = builder
                .rootUri(baseUrl)
                .additionalInterceptors(authInterceptor)
                // TODO ADD additional config here if needed
                .build();

        this.baseUrl = baseUrl;
        this.rateLimiter = new RateLimiter(MAX_REQUEST_PER_SECOND);
    }

    // TODO: add a retry and better error handling
    // TODO: use an iterator for better memory usage
    // TODO: add possibility to configure the number of elements retrieved per page, instead of always the max allowed
    public Collection<JobOfferDto> searchOffers(final Map<String, String> filters) {
        Collection<JobOfferDto> allOffers = new ArrayList<>();

        int start = 0;
        boolean hasMore = true;

        // TODO add more log to know if MAX_RANGE_START_VALUE is reached
        while (hasMore && start < MAX_RANGE_START_VALUE) {
            int end = start + MAX_ELEMENT_PER_PAGE - 1;
            String rangeValue = start + "-" + end;

            rateLimiter.acquire();
            List<JobOfferDto> partialJobOffers = fetchPage(buildQueryParams(rangeValue, filters));

            if (partialJobOffers.isEmpty()) {
                hasMore = false;
            } else {
                allOffers.addAll(partialJobOffers);
                if (partialJobOffers.size() < MAX_ELEMENT_PER_PAGE) {
                    hasMore = false;
                } else {
                    start += MAX_ELEMENT_PER_PAGE;
                }
            }
        }

        return allOffers;
    }

    private List<JobOfferDto> fetchPage(final MultiValueMap<String, String> params) {
        String uri = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/offres/search")
                .queryParams(params)
                .build()
                .toUriString();

        JobOfferResponseDto response = restTemplate.getForObject(uri, JobOfferResponseDto.class);

        if (response != null && response.getResultats() != null) {
            return response.getResultats();
        }

        return Collections.emptyList();
    }

    private MultiValueMap<String, String> buildQueryParams(final String rangeValue, final Map<String, String> filters) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        filters.forEach(params::add);
        params.add("range", rangeValue);
        return params;
    }

}
