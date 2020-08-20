package econo.webper.server.login;

import econo.webper.server.login.dto.GoogleUserinfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleLoginService {

    private final RestTemplate restTemplate;

    @Value("${social.google.url}")
    private String googleRequestUrl;

    public GoogleLoginService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity authenticate(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(httpHeaders);
        try {
            return restTemplate.exchange(googleRequestUrl, HttpMethod.GET, request, GoogleUserinfoDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
