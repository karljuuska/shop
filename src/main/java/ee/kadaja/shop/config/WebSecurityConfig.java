package ee.kadaja.shop.config;


import java.util.Arrays;
import java.util.Collections;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class WebSecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        val configurationSource = new UrlBasedCorsConfigurationSource();
        val corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);

        // In production, use a proper allowed source list and/or reverse proxy.
        // If you are good with CORS and decide to do Bonus Task, feel free to edit the following.
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS"));
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(configurationSource);
    }
}
