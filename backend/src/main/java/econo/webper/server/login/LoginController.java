package econo.webper.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.domain.User;
import econo.webper.server.util.JsonExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

@RestController
public class LoginController {

    @Autowired
    ObjectMapper objectMapper;


    @PostMapping("/login/google")
    public ResponseEntity loginByGoogleOAuth(@RequestBody String requestBody) {
        String accessToken = (String) JsonExtractor.getValueByKey(requestBody, "access_token");

        String url = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer" + accessToken);
        HttpEntity request = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<User> userResponseEntity = restTemplate.exchange(url, HttpMethod.GET, request, User.class, 1);
        System.out.println(userResponseEntity.getStatusCode());


        return ResponseEntity.ok(accessToken);

    }


}
