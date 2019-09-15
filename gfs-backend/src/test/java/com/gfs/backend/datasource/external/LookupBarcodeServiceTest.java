package com.gfs.backend.datasource.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfs.backend.model.BarcodeData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LookupBarcodeServiceTest {
    String jsonResponse = "{\n" +
            "    \"products\": [\n" +
            "        {\n" +
            "            \"barcode_number\": \"5901588016030\",\n" +
            "            \"barcode_type\": \"EAN\",\n" +
            "            \"barcode_formats\": \"EAN 5901588016030\",\n" +
            "            \"mpn\": \"\",\n" +
            "            \"model\": \"\",\n" +
            "            \"asin\": \"B00892EPPM\",\n" +
            "            \"product_name\": \"E. Wedel Dark Chocolate (100g/3.5oz)\",\n" +
            "            \"title\": \"\",\n" +
            "            \"category\": \"Food, Beverages & Tobacco > Food Items > Candy & Chocolate\",\n" +
            "            \"manufacturer\": \"Lotte Wedel\",\n" +
            "            \"brand\": \"E.Wedel\",\n" +
            "            \"label\": \"Lotte Wedel\",\n" +
            "            \"author\": \"\",\n" +
            "            \"publisher\": \"Lotte Wedel\",\n" +
            "            \"artist\": \"\",\n" +
            "            \"actor\": \"\",\n" +
            "            \"director\": \"\",\n" +
            "            \"studio\": \"\",\n" +
            "            \"genre\": \"\",\n" +
            "            \"audience_rating\": \"\",\n" +
            "            \"ingredients\": \"\",\n" +
            "            \"nutrition_facts\": \"\",\n" +
            "            \"color\": \"\",\n" +
            "            \"format\": \"\",\n" +
            "            \"package_quantity\": \"\",\n" +
            "            \"size\": \"\",\n" +
            "            \"length\": \"\",\n" +
            "            \"width\": \"\",\n" +
            "            \"height\": \"\",\n" +
            "            \"weight\": \"31\",\n" +
            "            \"release_date\": \"\",\n" +
            "            \"description\": \"Dark Chocolate - Chocolat Noir\",\n" +
            "            \"features\": [\n" +
            "                \"cocoa solids 64 % minimum\"\n" +
            "            ],\n" +
            "            \"images\": [\n" +
            "                \"https://images.barcodelookup.com/4574/45742971-1.jpg\",\n" +
            "                \"https://images.barcodelookup.com/4574/45742971-2.jpg\"\n" +
            "            ],\n" +
            "            \"stores\": [],\n" +
            "            \"reviews\": []\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Test
    public void jsonMapper() throws IOException {
        int expected = 31;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonResponse);
        int weight = actualObj.get("products").get(0).get("weight").asInt();
        assertEquals(weight, expected);
    }

    @Autowired
    LookupBarcodeService lookupBarcodeService;

    @Test
    public void shouldSatisfyMyColleques() {
        String barcode = "5901588016030";
        Optional<BarcodeData> data = lookupBarcodeService.lookup(barcode);
        System.out.println(data);
        assertEquals(data.get().getWeight().get().getWeightInGrams(), 31);
    }

}