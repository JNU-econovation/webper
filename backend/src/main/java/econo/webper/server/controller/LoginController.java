package econo.webper.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.util.JsonExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    ObjectMapper objectMapper;


    @PostMapping("/login/google")
    public ResponseEntity loginByGoogleOAuth(@RequestBody String requestBody) {
        String accessToken = (String) JsonExtractor.getValueByKey(requestBody, "access_token");


        return ResponseEntity.ok(accessToken);

    }


}
