package econo.webper.server.login;

import econo.webper.server.domain.UserService;
import econo.webper.server.util.JsonExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoogleLoginController {

    GoogleLoginService googleLoginService;

    UserService userService;

    public GoogleLoginController(GoogleLoginService googleLoginService, UserService userService) {
        this.googleLoginService = googleLoginService;
        this.userService = userService;
    }

    @PostMapping("/login/google")
    public ResponseEntity loginByGoogleOAuth(@RequestBody String requestBody) {
        String accessToken = (String) JsonExtractor.getValueByKey(requestBody, "access_token");
        ResponseEntity responseEntity = googleLoginService.authenticate(accessToken);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            userService.saveUser((GoogleUserinfoDTO) responseEntity.getBody());
        }
        return responseEntity;
    }
}
