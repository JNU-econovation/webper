package econo.webper.server.login;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleLoginService {

    RestTemplate restTemplate;

    public GoogleLoginService() {
        restTemplate = new RestTemplate();
    }

    public ResponseEntity authenticate(String accessToken) {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer" + accessToken);
        HttpEntity request = new HttpEntity(httpHeaders);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, GoogleUserinfoDTO.class, 1);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
