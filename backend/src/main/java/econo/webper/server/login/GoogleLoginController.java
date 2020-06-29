package econo.webper.server.login;

import econo.webper.server.domain.User;
import econo.webper.server.domain.UserRole;
import econo.webper.server.domain.UserService;
import econo.webper.server.jwt.JwtTokenProvider;
import econo.webper.server.util.JsonExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GoogleLoginController {

    GoogleLoginService googleLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    UserService userService;

    public GoogleLoginController(GoogleLoginService googleLoginService, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.googleLoginService = googleLoginService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login/google")
    public ResponseEntity loginByGoogleOAuth(@RequestBody String requestBody) {
        String accessToken = JsonExtractor.getValueByKey(requestBody, "access_token");
        ResponseEntity responseEntity = googleLoginService.authenticate(accessToken);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            GoogleUserinfoDTO googleUserinfoDTO = (GoogleUserinfoDTO) responseEntity.getBody();
            userService.saveUser(googleUserinfoDTO);
            String token = jwtTokenProvider.createToken(googleUserinfoDTO.getEmail(), Collections.singletonList(UserRole.USER));
            return ResponseEntity.ok(convertStringToMap(token));
        }
        return ResponseEntity.badRequest().build();
    }

    private Map<String, String> convertStringToMap(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("X-AUTH-TOKEN", token);
        return map;
    }

    @GetMapping("/test")
    public ResponseEntity test(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}
