package econo.webper.server.login;


import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.domain.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GoogleLoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void GoogleLoginWithWrongAccessToken() throws Exception {
        // Given
        String GoogleAccessToken =  "ya29.a0AfH6SMDcsCYxMGMUaZZPVY6vFpvvIRYzp6LD6XbKLwILHoc0tegOKe8QDiMyAIwlH5r5sm6oUKejAIjb883OIpK7wTnPPChT1ph70uactcFcmT5wAKyHYGORVw6q9kly2zHxwU7oHS2-vePBf8bn86WVkjznt77hV4A";

        // When & Then
        this.mockMvc.perform(post("/login/google")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"access_token\" : \"" + GoogleAccessToken + "\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}