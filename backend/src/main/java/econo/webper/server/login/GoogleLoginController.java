package econo.webper.server.login;

import econo.webper.server.login.dto.GoogleAccessTokenDTO;
import econo.webper.server.login.dto.GoogleUserinfoDTO;
import econo.webper.server.member.MemberRole;
import econo.webper.server.member.MemberService;
import econo.webper.server.security.jwt.JwtTokenProvider;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "Google Login", tags = {"Google Login - 담당자 : 배종진"})
@RestController
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public GoogleLoginController(GoogleLoginService googleLoginService, JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.googleLoginService = googleLoginService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @ApiOperation(value = "Login By Google OAuth", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login Success"),
            @ApiResponse(code = 401, message = "Login Failed")
    })
    @PostMapping("/login/google")
    public ResponseEntity loginByGoogleOAuth(@RequestBody GoogleAccessTokenDTO googleAccessTokenDTO) {
        ResponseEntity responseEntity = googleLoginService.authenticate(googleAccessTokenDTO.getAccess_token());
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            GoogleUserinfoDTO googleUserinfoDTO = (GoogleUserinfoDTO) responseEntity.getBody();
            List<MemberRole> memberRoles = Collections.singletonList(MemberRole.USER);
            memberService.saveMember(googleUserinfoDTO, memberRoles);
            String token = jwtTokenProvider.createToken(googleUserinfoDTO.getEmail(), memberRoles);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(convertStringToMap(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionMessage.GOOGLE_OAUTH_ACCESS_TOKEN_UNVALIDATED);
    }

    private Map<String, String> convertStringToMap(String token) {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);
        return map;
    }

}
