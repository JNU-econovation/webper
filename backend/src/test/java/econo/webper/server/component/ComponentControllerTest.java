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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        mockMvc.perform(get("/directory/1/components")
                .header("Authorization", token))
                .andDo(print());
        mockMvc.perform(get("/component")
                .param("id", "1")
                .header("Authorization", token))
                .andDo(print());
        mockMvc.perform(get("/component")
                .param("id", "2")
                .header("Authorization", token))
                .andDo(print());
        mockMvc.perform(get("/component")
                .param("id", "3")
                .header("Authorization", token))
                .andDo(print());
        jsonParam = "{" +
                "\"description\" : \"Changed Description\"," +
                "\"redirectionLink\" : \"Changed RedirectionLink\"," +
                "\"thumbnailURL\" : \"Changed ThumbnailURL\"," +
                "\"id\" : 1," +
                "\"title\" : \"Changed Title\"" +
                "}";
        mockMvc.perform(patch("/component/blog")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"PORTAL\"," +
                "\"parentDirectoryId\" : null," +
                "\"title\" : \"Portal Directory Example\"" +
                "}";

        mockMvc.perform(post("/directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"PORTAL\"," +
                "\"description\" : \"Portal Example\"," +
                "\"directoryId\" : 2," +
                "\"faviconURL\" : \"FaviconURL Example\"," +
                "\"redirectionLink\" : \"RedirectionLink Example\"," +
                "\"title\" : \"Title Example\"" +
                "}";
        mockMvc.perform(post("/component/portal")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"description\" : \"Changed Description\"," +
                "\"redirectionLink\" : \"Changed RedirectionLink\"," +
                "\"faviconURL\" : \"Changed faviconURL\"," +
                "\"id\" : 2," +
                "\"title\" : \"Changed Title\"" +
                "}";

        mockMvc.perform(patch("/component/portal")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"VIDEO\"," +
                "\"parentDirectoryId\" : null," +
                "\"title\" : \"Video Directory Example\"" +
                "}";

        mockMvc.perform(post("/directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"VIDEO\"," +
                "\"directoryId\" : 3," +
                "\"thumbnailURL\" : \"ThumbnailURL Example\"," +
                "\"redirectionLink\" : \"RedirectionLink Example\"," +
                "\"title\" : \"Title Example\"" +
                "}";
        mockMvc.perform(post("/component/video")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"thumbnailURL\" : \"Changed thumbnailURL\"," +
                "\"redirectionLink\" : \"Changed RedirectionLink\"," +
                "\"id\" : 3," +
                "\"title\" : \"Changed Title\"" +
                "}";

        mockMvc.perform(patch("/component/video")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());


        jsonParam = "{" +
                "\"category\" : \"WISHLIST\"," +
                "\"parentDirectoryId\" : null," +
                "\"title\" : \"WishList Directory Example\"" +
                "}";

        mockMvc.perform(post("/directory")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"category\" : \"WISHLIST\"," +
                "\"directoryId\" : 4," +
                "\"deliveryInfo\" : \"deliveryInfo Example\"," +
                "\"description\" : \"description Example\"," +
                "\"price\" : \"price Example\"," +
                "\"redirectionLink\" : \"RedirectionLink Example\"," +
                "\"shoppingMall\" : \"ShoppingMall Example\"," +
                "\"thumbnailURL\" : \"ThumbnailURL Example\"," +
                "\"title\" : \"Title Example\"" +
                "}";

        mockMvc.perform(post("/component/wishlist")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        jsonParam = "{" +
                "\"id\" : 4," +
                "\"deliveryInfo\" : \"Changed DeliveryInfo\"," +
                "\"description\" : \"Changed Description\"," +
                "\"price\" : \"Changed Price\"," +
                "\"shoppingMall\" : \"Changed ShoppingMall\"," +
                "\"thumbnailURL\" : \"Changed ThumbnailURL\"," +
                "\"title\" : \"Changed Title\"" +
                "}";

        mockMvc.perform(patch("/component/wishlist")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonParam))
                .andDo(print());

        mockMvc.perform(get("/directory/4/components")
                .header("Authorization", token))
                .andDo(print());

        mockMvc.perform(delete("/component/4")
                .header("Authorization", token))
                .andDo(print());

        mockMvc.perform(get("/directory/4/components")
                .header("Authorization", token))
                .andDo(print());
    }
}