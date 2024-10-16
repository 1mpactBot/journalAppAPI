package dev.saransh.journalApp.service;

import dev.saransh.journalApp.api.response.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuotesService {
    private static final String API = "https://api.kanye.rest";
    @Autowired
    RestTemplate restTemplate;

    public String getQuote() {
        ResponseEntity<QuoteResponse> res = restTemplate.exchange(API, HttpMethod.GET, null, QuoteResponse.class);
        QuoteResponse response = res.getBody();
        if (response != null) {
            return response.getQuote();
        }
        return "";
    }
}
