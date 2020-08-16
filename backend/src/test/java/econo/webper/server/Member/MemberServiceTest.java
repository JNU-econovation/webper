package econo.webper.server.member;

import econo.webper.server.login.GoogleUserinfoDTO;
import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberRole;
import econo.webper.server.Member.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void saveUserTest() {
        // Given
        String email = "jongjin@email.com";
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail(email);

        // When
        Member member = memberService.saveMember(googleUserinfoDTO, Collections.singletonList(MemberRole.USER));

        // Then
        assertThat(member.getEmail()).isEqualTo(email);
    }

    @Test
    public void savedUserTest() {
        // Given
        String email = "jongjin@email.com";
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail(email);

        // When
        Member member = memberService.saveMember(googleUserinfoDTO, Collections.singletonList(MemberRole.USER));
        Member savedMember = memberService.saveMember(googleUserinfoDTO, Collections.singletonList(MemberRole.USER));

        // Then
        assertThat(member).isEqualTo(savedMember);
    }

    @Test
    public void findUserByEmailTest() {
        // Given
        String email = "jongjin@email.com";
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail(email);
        memberService.saveMember(googleUserinfoDTO, Collections.singletonList(MemberRole.USER));

        // when
        Member memberDetails = memberService.findMemberByEmail(googleUserinfoDTO.getEmail());

        // Then
        assertThat(memberDetails.getEmail()).isEqualTo(email);
    }

}