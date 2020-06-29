package econo.webper.server.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleLoginServiceTest {

    @Autowired
    GoogleLoginService googleLoginService;

    @Test
    public void authenticateWithWrongAccessToken() {
        ResponseEntity responseEntity = googleLoginService.authenticate("sdjklfdsjklfsdjkl");
        assertThat(responseEntity.getStatusCode().is4xxClientError()).isTrue();
    }

}