package econo.webper.server.login;


import econo.webper.server.domain.UserService;
import econo.webper.server.jwt.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GoogleLoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GoogleLoginService googleLoginService;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void GoogleLoginWithWrongAccessToken() throws Exception {
        // When & Then
        when(googleLoginService.authenticate(eq("Wrong_Access_Token")))
                .thenReturn(ResponseEntity.badRequest().build());

        // Then
        this.mockMvc.perform(post("/login/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"access_token\" : \"Wrong_Access_Token\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void GoogleLoginWithRightAccessToken() throws Exception {
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail("frog@email.com");
        ResponseEntity<GoogleUserinfoDTO> responseEntity = ResponseEntity.ok(googleUserinfoDTO);

        // When
        when(googleLoginService.authenticate(eq("Right_Access_Token")))
                .thenReturn(responseEntity);

        this.mockMvc.perform(post("/login/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"access_token\" : \"Right_Access_Token\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}