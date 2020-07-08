package econo.webper.server.login;

import econo.webper.server.domain.UserRole;
import econo.webper.server.domain.UserService;
import econo.webper.server.jwt.JwtTokenProvider;
import econo.webper.server.util.JsonExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

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
            List<UserRole> userRoles = Collections.singletonList(UserRole.USER);
            userService.saveUser(googleUserinfoDTO, userRoles);
            String token = jwtTokenProvider.createToken(googleUserinfoDTO.getEmail(), userRoles);
            return ResponseEntity.ok(convertStringToMap(token));
        }
        return ResponseEntity.badRequest().build();
    }

    private Map<String, String> convertStringToMap(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("X-AUTH-TOKEN", token);
        return map;
    }

}
