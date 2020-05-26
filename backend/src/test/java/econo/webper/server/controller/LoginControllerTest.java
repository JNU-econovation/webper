package econo.webper.server.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void GoogleLoginTest() throws Exception {
        // Given
        String GoogleAccessToken =  "ya29.a0AfH6SMBlvG3pCM6wHUBfW1jiTaM39NGHao4avWJkdpNCqMCjI0_uO30B45SVtIyEx1LfGBv-i8SAs-a3eRM0IvYBVjtK1CWqze27ZhpBCQ_MEIbu1yjQOt96Wgq7xlQEpeVjBy8vW3lE_FlbdmlvOjIVElDy718L2F4";

        // When & Then
        this.mockMvc.perform(post("/login/google")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"access_token\" : \"" + GoogleAccessToken + "\"}"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}