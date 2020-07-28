package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberRepository;
import econo.webper.server.Member.MemberRole;
import econo.webper.server.jwt.JwtTokenProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class DirectoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    public void test() throws Exception {
        Member member = Member.builder()
                .email("qowhdwlsdk@gmail.com")
                .name("Jongjin")
                .roles(Collections.singletonList(MemberRole.USER))
                .build();

        memberRepository.save(member);

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());

        String jsonParam = "{" +
                "\"category\" : \"BLOG\"," +
                "\"parentDirectoryId\" : null," +
                "\"title\" : \"Example\"" +
                "}";

        mockMvc.perform(post("/Directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());
        mockMvc.perform(post("/Directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());
        jsonParam = "{" +
                "\"category\" : \"BLOG\"," +
                "\"parentDirectoryId\" : 1," +
                "\"title\" : \"Example\"" +
                "}";
        mockMvc.perform(post("/Directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"BLOG\"," +
                "\"parentDirectoryId\" : 2," +
                "\"title\" : \"Example\"" +
                "}";
        mockMvc.perform(post("/Directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        mockMvc.perform(get("/Directory")
                .header("Authorization", token)
                .param("id","1"))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"BLOG\"," +
                "\"id\" : 1," +
                "\"title\" : \"Change Example\"" +
                "}";

        mockMvc.perform(put("/Directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        mockMvc.perform(delete("/Directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());




    }
}









