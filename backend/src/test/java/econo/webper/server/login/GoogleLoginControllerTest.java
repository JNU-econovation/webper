package econo.webper.server.login;

import econo.webper.server.config.SecurityConfig;
import econo.webper.server.member.MemberService;
import econo.webper.server.security.jwt.JwtTokenProvider;
import econo.webper.server.utils.ExceptionMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GoogleLoginController.class, SecurityConfig.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@AutoConfigureWebMvc
public class GoogleLoginControllerTest {
    private static final String WRONG_ACCESS_TOKEN = "wrong access token";
    private static final String RIGHT_ACCESS_TOKEN = "right access token";
    private static final String GOOGLE_EMAIL = "qqq@gmail.com";
    private static final String JWT_TOKEN = "sadfkjlsadjkfl";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GoogleLoginService googleLoginService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    MemberService memberService;

    @Test
    public void GoogleLoginWithRightAccessToken() throws Exception {
        String json = "{\"access_token\" : \"" + RIGHT_ACCESS_TOKEN + "\" }";
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO(GOOGLE_EMAIL);
        ResponseEntity<GoogleUserinfoDTO> responseEntity = ResponseEntity.ok(googleUserinfoDTO);

        // When
        when(googleLoginService.authenticate(eq(RIGHT_ACCESS_TOKEN))).thenReturn(responseEntity);
        when(jwtTokenProvider.createToken(eq(GOOGLE_EMAIL), anyList())).thenReturn(JWT_TOKEN);

        this.mockMvc.perform(post("/login/google")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Authorization").value(JWT_TOKEN));
    }

    @Test
    public void GoogleLoginWithWrongAccessToken() throws Exception {
        String json = "{\"access_token\" : \"" + WRONG_ACCESS_TOKEN + "\" }";

        // When & Then
        when(googleLoginService.authenticate(eq(WRONG_ACCESS_TOKEN)))
                .thenReturn(ResponseEntity.badRequest().build());

        // Then
        this.mockMvc.perform(post("/login/google")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(ExceptionMessage.GOOGLE_OAUTH_ACCESS_TOKEN_UNVALIDATED));
    }
}