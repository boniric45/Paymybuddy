package fr.boniric.paymybuddy.web.custom;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "fr.boniric.paymybuddy.web")
public class CustomProperties {
    private String apiURL;
}
