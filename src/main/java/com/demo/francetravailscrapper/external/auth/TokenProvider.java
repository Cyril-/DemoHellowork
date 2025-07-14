package com.demo.francetravailscrapper.external.auth;

import com.demo.francetravailscrapper.external.models.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

@Component
public class TokenProvider {

    private final RestTemplate restTemplate;
    private final String tokenUrl;
    private final String clientId;
    private final String clientSecret;
    private final String scope;

    private String cachedToken;
    private Instant expiryTime;

    public TokenProvider(
            final RestTemplateBuilder builder,
            @Value("${francetravail.auth.url}") String tokenUrl,
            @Value("${francetravail.auth.client-id}") String clientId,
            @Value("${francetravail.auth.client-secret}") String clientSecret,
            @Value("${francetravail.auth.scope}") String scope
    ) {
        this.restTemplate = builder
                .rootUri(tokenUrl)
                // TODO ADD additional config here if needed
                .build();
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    public String getToken() {
        if (cachedToken == null || Instant.now().isAfter(expiryTime)) {
            fetchToken();
        }
        return cachedToken;
    }

    private void fetchToken() {
        String uri = UriComponentsBuilder
                .fromUriString(tokenUrl)
                .queryParams(buildQueryParams())
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                TokenResponse.class
        );

        TokenResponse tokenResponse = response.getBody();
        cachedToken = tokenResponse.getAccessToken();
        Integer expiresIn = tokenResponse.getExpiresIn();
        if (expiresIn != null) {
            // TODO is expiresIn in second in response ?
            expiryTime = Instant.now().plusSeconds(expiresIn - 60);
        }
    }

    private MultiValueMap<String, String> buildQueryParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("scope", scope);
        params.add("realm", "/partenaire");
        return params;
    }
}
