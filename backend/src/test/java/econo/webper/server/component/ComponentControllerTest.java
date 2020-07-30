package econo.webper.server.component;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ComponentControllerTest {

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

        mockMvc.perform(post("/directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());
        mockMvc.perform(post("/directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"BLOG\"," +
                "\"description\" : \"Description Example\"," +
                "\"directoryId\" : 1," +
                "\"redirectionLink\" : \"RedirectionLink Example\"," +
                "\"thumbnailURL\" : \"ThumbnailURL Example\"," +
                "\"title\" : \"Title Example\"" +
                "}";

        mockMvc.perform(post("/component/blog")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());
        mockMvc.perform(post("/component/blog")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());
        mockMvc.perform(get("/directory/1/components")
                .header("Authorization", token))
                .andDo(print());
    }


}