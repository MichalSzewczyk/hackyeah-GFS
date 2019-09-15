package com.gfs.backend.configuration;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class CountryConfiguration {
    private final Map<String, String> countryMapping;

    public CountryConfiguration() {
        countryMapping = new HashMap<>();
        countryMapping.put("US", "000-049");
        countryMapping.put("FRANCE", "380-380");
        countryMapping.put("BULGARIA", "383-383");
        countryMapping.put("SLOVENIA", "383-383");
        countryMapping.put("CROATIA", "385-385");
        countryMapping.put("CROATIA", "385-385");
        countryMapping.put("GERMANY", "400-440");
        countryMapping.put("JAPAN", "450-499");
        countryMapping.put("RUSSIA", "460-469");
        countryMapping.put("POLAND", "590-590");
        countryMapping.put("ROMANIA", "594-594");
        countryMapping.put("HUNGARY", "599-599");
        countryMapping.put("TUNISIA", "619-619");
        countryMapping.put("CHINA", "690-695");
        countryMapping.put("FINLAND", "640-649");
        countryMapping.put("NORWAY", "700-709");
        countryMapping.put("ITALY", "800-839");
        countryMapping.put("SPAIN", "840-849");
        countryMapping.put("SLOVAKIA", "858-858");
        countryMapping.put("CZECH", "859-859");
        countryMapping.put("THAILAND", "885-885");
        countryMapping.put("INDIA", "890-890");
        countryMapping.put("AUSTRIA", "900-919");
        countryMapping.put("AUSTRALIA", "930-939");
    }
}
