package com.gfs.backend.datasource.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfs.backend.datasource.BarcodeService;
import com.gfs.backend.model.BarcodeData;
import com.gfs.backend.model.barcode.Weight;
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

@Service
@Slf4j
public class LookupBarcodeService implements BarcodeService<BarcodeData> {

    private final URI uri;
    private final String key;

    public LookupBarcodeService(@Value("${barcodeAPI.URI}") URI uri, @Value("${barcodeAPI.key}") String key) {
        this.uri = uri;
        this.key = key;
    }

    private BarcodeData extractData(String jsonResponse) throws IOException {
        log.info("Processing product response. [jsonResponse={}]", jsonResponse);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonResponse);
        int weight = actualObj.get("products").get(0).get("weight").asInt();
        String productName = actualObj.get("products").get(0).get("product_name").asText();
        BarcodeData barcodeData = new BarcodeData();
        barcodeData.setWeight(new Weight(weight));
        barcodeData.setProductName(productName);
        return barcodeData;
    }

    @Override
    public Optional<BarcodeData> lookup(String barcode) {

//        # https://api.barcodelookup.com/v2/products?barcode=5901588016030&formatted=y&key=rscqc9nfchqqam6csdy0tcvy1akem6

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("barcode", barcode)
                .queryParam("formatted", "y")
                .queryParam("key", key);

        ResponseEntity<String> response;
        RestTemplate restTemplate = new RestTemplate();
        try {
            response = restTemplate.getForEntity(builder.toUriString(), String.class);
        } catch (RestClientException exception) {
            log.warn("Product not resolved. [responseMessage={}]", exception.getMessage());
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(extractData(response.getBody()));
        } catch (IOException exception) {
            log.error("Exception thrown.", exception);
            return Optional.empty();
        }
    }
}
