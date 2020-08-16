package econo.webper.server.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.directory.DirectoryCategory;
import econo.webper.server.directory.DirectoryService;
import econo.webper.server.directory.dto.DirectoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MemberController.class, ObjectMapper.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class MemberControllerTest {

    private static final String EMAIL = "a@a";
    private static final Integer ID = 1;
    private static final String NAME = "NAME";
    private static final String PASSWORD = "1234";

    private static final Integer DIRECTORY_ID = 1;
    private static final String DIRECTORY_TITLE = "title";
    private static final Integer PARENT_DIRECTORY_ID = null;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectoryService directoryService;

    @MockBean
    private MemberService memberService;


    @DisplayName("멤버에 Root Directory가 존재하지 않는 경우 테스트")
    @WithUserDetails
    @Test
    public void getNonRootDirectoryTest() throws Exception {
        Member member = new Member().builder()
                .id(ID)
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .roles(Collections.singletonList(MemberRole.USER))
                .build();
        List<DirectoryDTO> nonElementList = new ArrayList<>();

        when(memberService.findMemberByEmail(any())).thenReturn(member);
        when(directoryService.getDirectoryDTOs(member)).thenReturn(nonElementList);
        mockMvc.perform(get("/root-directory"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @DisplayName("멤버에 Root Directory가 존재하는 경우 테스트")
    @WithUserDetails
    @Test
    public void getRootDirectoryTest() throws Exception {
        DirectoryDTO directoryDTO = new DirectoryDTO(DIRECTORY_ID, DIRECTORY_TITLE, DirectoryCategory.BLOG, PARENT_DIRECTORY_ID);
        Member member = new Member().builder()
                .id(ID)
                .email(EMAIL)
                .name(NAME)
                .password(PASSWORD)
                .roles(Collections.singletonList(MemberRole.USER))
                .build();

        when(memberService.findMemberByEmail(any())).thenReturn(member);
        when(directoryService.getDirectoryDTOs(member))
                .thenReturn(Collections.singletonList(directoryDTO));

        mockMvc.perform(get("/root-directory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(DIRECTORY_ID))
                .andExpect(jsonPath("$[0].title").value(DIRECTORY_TITLE))
                .andExpect(jsonPath("$[0].category").value(DirectoryCategory.BLOG.name()))
                .andExpect(jsonPath("$[0].parentDirectoryId").value(PARENT_DIRECTORY_ID));
    }

}