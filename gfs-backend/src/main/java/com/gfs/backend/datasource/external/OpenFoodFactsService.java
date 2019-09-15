package com.gfs.backend.datasource.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfs.backend.datasource.BarcodeService;
import com.gfs.backend.model.OpenFoodData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class OpenFoodFactsService implements BarcodeService<OpenFoodData> {
    private final URI uri;

    public OpenFoodFactsService(@Value("${openFoodFacts.URI}") URI uri) {
        this.uri = uri;
    }

    private Optional<OpenFoodData> extractData(String jsonResponse) throws IOException {
        log.info("Processing product response. [jsonResponse={}]", jsonResponse);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonResponse);
        if (isNotFound(actualObj)) {
            return Optional.empty();
        }
        if (isNull(actualObj.get("status_verbose")) || isNull(actualObj.get("product").get("ingredients_from_palm_oil_n"))) {
            return Optional.empty();
        }
        boolean isOilPalm = actualObj.get("product").get("ingredients_from_palm_oil_n").asBoolean();
        OpenFoodData openFoodData = new OpenFoodData();
        openFoodData.setOilPalm(isOilPalm);
        return Optional.of(openFoodData);
    }

    private boolean isNotFound(JsonNode actualObj) {
        return nonNull(actualObj.get("status_verbose")) && "product not found".equals(actualObj.get("status_verbose").textValue());
    }

    public Optional<OpenFoodData> lookup(String barcode) {

        String url = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .path("/" + barcode).toUriString();

        ResponseEntity<String> response;
        RestTemplate restTemplate = new RestTemplate();
        try {
            response = restTemplate.getForEntity(url, String.class);
        } catch (RestClientException exception) {
            log.warn("Product not resolved. [responseMessage={}]", exception.getMessage());
            return Optional.empty();
        }
        try {
            return extractData(response.getBody());
        } catch (IOException exception) {
            log.error("Exception thrown.", exception);
            return Optional.empty();
        }
    }
}
