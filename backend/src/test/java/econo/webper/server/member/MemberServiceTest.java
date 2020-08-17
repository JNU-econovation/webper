package econo.webper.server.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.component.Component;
import econo.webper.server.component.ComponentCategory;
import econo.webper.server.directory.Directory;
import econo.webper.server.directory.DirectoryCategory;
import econo.webper.server.directory.dto.CreateDirectoryDTO;
import econo.webper.server.directory.dto.DirectoryDTO;
import econo.webper.server.exception.NoSuchDirectoryException;
import econo.webper.server.exception.NoSuchMemberException;
import econo.webper.server.exception.NotSaveDirectoryException;
import econo.webper.server.login.GoogleUserinfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MemberService.class, ObjectMapper.class})
public class MemberServiceTest {
    private static final int MEMBER_ID = 1;
    private static final String MEMBER_EMAIL = "a@a";
    private static final String MEMBER_NAME = "frog";
    private static final String MEMBER_PASSWORD = "1234";
    private static final int PARENT_DIRECTORY_ID = 1;
    private static final int DIRECTORY_ID = 2;
    private static final String DIRECTORY_TITLE = "directory title";
    private static final String COMPONENT_TITLE = "component title";

    @Autowired
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @DisplayName("DB에 저장되지 않는 Member 저장시 테스트")
    @Test
    public void saveMemberTest() {
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail(MEMBER_EMAIL);
        List<MemberRole> memberRoles = Collections.singletonList(MemberRole.USER);
        Member member = Member.builder()
                .email(MEMBER_EMAIL)
                .roles(memberRoles)
                .name(MEMBER_NAME)
                .password(MEMBER_PASSWORD)
                .build();

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(null));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        Member savedMember = memberService.saveMember(googleUserinfoDTO, memberRoles);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("DB에 Member 중복 저장시 검색 테스트")
    @Test
    public void saveSavedMemberTest() {
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail(MEMBER_EMAIL);
        List<MemberRole> memberRoles = Collections.singletonList(MemberRole.USER);
        Member member = Member.builder()
                .email(MEMBER_EMAIL)
                .roles(memberRoles)
                .name(MEMBER_NAME)
                .password(MEMBER_PASSWORD)
                .build();
        Optional<Member> optionalMember = Optional.of(member);

        when(memberRepository.findByEmail(MEMBER_EMAIL)).thenReturn(optionalMember);
        Member savedMember = memberService.saveMember(googleUserinfoDTO, memberRoles);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("이메일로 Member 검색 테스트")
    @Test
    public void findMemberByEmailTest() {
        Member member = Member.builder()
                .email(MEMBER_EMAIL)
                .roles(Collections.singletonList(MemberRole.USER))
                .name(MEMBER_NAME)
                .password(MEMBER_PASSWORD)
                .build();

        when(memberRepository.findByEmail(MEMBER_EMAIL)).thenReturn(Optional.of(member));
        Member savedMember = memberService.findMemberByEmail(MEMBER_EMAIL);

        assertThat(savedMember).isEqualTo(member);
    }

    @DisplayName("이메일로 Member 검색시 NoSuchElementException 테스트")
    @Test
    public void findMemberByEmailExceptionTest() {
        when(memberRepository.findByEmail(MEMBER_EMAIL)).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchElementException.class, () -> {
            memberService.findMemberByEmail(MEMBER_EMAIL);
        });
    }

    @DisplayName("Member에 Directory 저장 테스트")
    @Test
    public void setDirectoryTest() {
        Directory parentDirectory = null;
        Member member = mock(Member.class);
        CreateDirectoryDTO createDirectoryDTO = new CreateDirectoryDTO(null, DIRECTORY_TITLE, DirectoryCategory.BLOG);
        Directory savedDirectory = Directory.builder().build();

        when(member.findDirectoryById(PARENT_DIRECTORY_ID)).thenReturn(parentDirectory);
        when(member.saveDirectory(any(Directory.class))).thenReturn(savedDirectory);

        assertThat(memberService.saveDirectory(member, createDirectoryDTO)).isEqualTo(savedDirectory);
    }

    @DisplayName("Member에 Directory 저장 Exception 테스트")
    @Test
    public void saveDirectoryInNotExistMemberExceptionTest() {
        Directory parentDirectory = null;
        Member member = mock(Member.class);
        CreateDirectoryDTO createDirectoryDTO = new CreateDirectoryDTO(PARENT_DIRECTORY_ID, DIRECTORY_TITLE, DirectoryCategory.BLOG);

        when(member.findDirectoryById(PARENT_DIRECTORY_ID)).thenReturn(parentDirectory);

        assertThrows(NotSaveDirectoryException.class, () -> {
            memberService.saveDirectory(member, createDirectoryDTO);
        });
    }

    @DisplayName("Directory 삭제 성공 테스트")
    @Test
    public void deleteDirectoryTest() {
        Member member = mock(Member.class);

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(member.deleteDirectory(DIRECTORY_ID)).thenReturn(true);

        assertThat(memberService.deleteDirectory(member, DIRECTORY_ID)).isTrue();
    }

    @DisplayName("Member가 존재하지 않을때 Directory 삭제 실패 테스트")
    @Test
    public void deleteDirectoryInNotExistMemberTest() {
        Member member = Member.builder()
                .id(MEMBER_ID)
                .name(MEMBER_NAME)
                .email(MEMBER_EMAIL)
                .password(MEMBER_PASSWORD)
                .roles(Collections.singletonList(MemberRole.USER))
                .build();

        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(null));

        assertThat(memberService.deleteDirectory(member, DIRECTORY_ID)).isFalse();
    }

    @DisplayName("Directory 삭제 실패 테스트")
    @Test
    public void deleteDirectoryFailTest() {
        Member member = mock(Member.class);

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(member.deleteDirectory(DIRECTORY_ID)).thenReturn(false);

        assertThat(memberService.deleteDirectory(member, DIRECTORY_ID)).isFalse();
    }

    @DisplayName("Directory 업데이트 테스트")
    @Test
    public void updateDirectoryTest() {
        Member member = mock(Member.class);
        DirectoryDTO directoryDTO = new DirectoryDTO();
        Directory directory = new Directory();

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(member.updateDirectory(directoryDTO)).thenReturn(directory);

        assertThat(memberService.updateDirectory(member, directoryDTO)).isEqualTo(directory);
    }

    @DisplayName("Member가 존재하지 않을 때 Directory 업데이트 실패 테스트")
    @Test
    public void updateDirectoryFailTest() {
        Member member = mock(Member.class);
        DirectoryDTO directoryDTO = new DirectoryDTO();

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchMemberException.class, () -> {
            memberService.updateDirectory(member, directoryDTO);
        });
    }

    @DisplayName("Member의 Directory 검색 테스트")
    @Test
    public void findDirectoryByIdTest() {
        Member member = mock(Member.class);
        Directory directory = new Directory();

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(member.findDirectoryById(DIRECTORY_ID)).thenReturn(directory);

        assertThat(memberService.findDirectoryById(member, DIRECTORY_ID)).isEqualTo(directory);
    }

    @DisplayName("Member가 존재하지 않을 때 Member의 Directory검색 시 예외 테스트")
    @Test
    public void findDirectoryByIdFailTest() {
        Member member = mock(Member.class);

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchMemberException.class, () -> {
            memberService.findDirectoryById(member, DIRECTORY_ID);
        });
    }

    @DisplayName("Member가 존재하지 않을 때 Component 저장 실패 테스트")
    @Test
    public void saveComponentInNotExistMemberTest() {
        Member member = mock(Member.class);
        Component component = new Component();

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchMemberException.class, () -> {
            memberService.saveComponent(member, component);
        });
    }

    @DisplayName("Component에 명시된 디렉토리가 존재하지 않을패 때 Component 저장 실패 테스트")
    @Test
    public void saveComponentInNotExistDirectoryTest() {
        Member member = mock(Member.class);
        Component component = new Component(COMPONENT_TITLE, DIRECTORY_ID, ComponentCategory.BLOG);

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(member));
        when(member.findDirectoryById(DIRECTORY_ID)).thenReturn(null);

        assertThrows(NoSuchDirectoryException.class, () -> {
            memberService.saveComponent(member, component);
        });
    }

    @DisplayName("Component와 Directory의 Category가 일치하지 않을 때 Component 저장 실패 테스트")
    @Test
    public void saveComponentWithNotMatchComponentAndDirectoryCategoryTest() {
        Member member = mock(Member.class);
        Component component = new Component(COMPONENT_TITLE, DIRECTORY_ID, ComponentCategory.BLOG);
        Directory directory = Directory.builder()
                .category(DirectoryCategory.WISHLIST)
                .build();

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(member));
        when(member.findDirectoryById(DIRECTORY_ID)).thenReturn(directory);

        assertThat(memberService.saveComponent(member, component)).isNull();
    }

    @DisplayName("Component가 저장 실패 테스트")
    @Test
    public void saveComponentFailTest() {
        Member member = mock(Member.class);
        Component component = new Component(COMPONENT_TITLE, DIRECTORY_ID, ComponentCategory.BLOG);
        Directory directory = mock(Directory.class);

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(member));
        when(member.findDirectoryById(DIRECTORY_ID)).thenReturn(directory);
        when(directory.getCategory()).thenReturn(DirectoryCategory.BLOG);
        when(directory.saveComponent(component)).thenReturn(false);

        assertThat(memberService.saveComponent(member, component)).isNull();
    }

    @DisplayName("Component 저장 테스트")
    @Test
    public void saveComponentTest() {
        Member member = mock(Member.class);
        Component component = new Component(COMPONENT_TITLE, DIRECTORY_ID, ComponentCategory.BLOG);
        Directory directory = Directory.builder()
                .category(DirectoryCategory.BLOG)
                .build();

        when(member.getId()).thenReturn(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.ofNullable(member));
        when(member.findDirectoryById(DIRECTORY_ID)).thenReturn(directory);

        assertThat(memberService.saveComponent(member, component)).isEqualTo(component);
    }


}