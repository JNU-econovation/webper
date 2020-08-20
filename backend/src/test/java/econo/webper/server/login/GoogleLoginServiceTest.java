package econo.webper.server.login;

import econo.webper.server.login.dto.GoogleUserinfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = GoogleLoginService.class)
@TestPropertySource(properties = "classpath:application.properties")
public class GoogleLoginServiceTest {
    private static final String GOOGLE_EMAIL = "abc@gmail.com";
    private static final String RIGHT_GOOGLE_ACCESS_TOKEN = "right access token";
    private static final String WRONG_GOOGLE_ACCESS_TOKEN = "wrong access token";

    @Autowired
    GoogleLoginService googleLoginService;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Value("${social.google.url}")
    private String googleRequestUrl;

    @DisplayName("올바른 Google Access Token을 가진 인증 테스트")
    @Test
    public void authenticateWithRightAccessTokenTest() {
        String expectResult = "{\"email\" : \"" + GOOGLE_EMAIL + "\"}";

        mockRestServiceServer.expect(requestTo(googleRequestUrl))
                .andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));
        ResponseEntity responseEntity = googleLoginService.authenticate(RIGHT_GOOGLE_ACCESS_TOKEN);
        GoogleUserinfoDTO body = (GoogleUserinfoDTO) responseEntity.getBody();

        assertThat(body.getEmail()).isEqualTo(GOOGLE_EMAIL);
    }

    @DisplayName("올바르지 못한 Google Access Token을 가진 인증 테스트")
    @Test
    public void authenticateWithWrongAccessTokenTest() {
        mockRestServiceServer.expect(requestTo(googleRequestUrl))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        ResponseEntity responseEntity = googleLoginService.authenticate(WRONG_GOOGLE_ACCESS_TOKEN);

        assertThat(responseEntity.getStatusCode().is4xxClientError()).isTrue();
    }
}