package econo.webper.server.security;

import econo.webper.server.member.Member;
import econo.webper.server.member.MemberRole;
import econo.webper.server.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserService.class)
class UserServiceTest {
    
    private static final String EMAIL = "user@user.com";
    private static final int ID = 1;
    private static final String PASSWORD = "pass";
    private static final String NAME = "name";
    private static final List<MemberRole> ROLES = Collections.singletonList(MemberRole.USER);
    
    @MockBean
    MemberService memberService;
    
    @Autowired
    UserService userService;
    
    @DisplayName("DB에 일치하는 Username을 가진 User가 있을 경우 테스트")
    @Test
    public void loadUserByUsernameTest() {
        Member member = Member.builder()
                .id(ID)
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .roles(ROLES)
                .build();
        
        when(memberService.findMemberByEmail(EMAIL)).thenReturn(member);
        UserDetails userDetails = userService.loadUserByUsername(EMAIL);
        
        assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
        assertThat(userDetails.getUsername()).isEqualTo(EMAIL);
    }
    
    @DisplayName("DB에 일치하는 Username을 가진 User가 없을 경우 예외 테스트")
    @Test
    public void loadUserByUsernameExceptionTest() {
        when(memberService.findMemberByEmail(EMAIL)).thenThrow(UsernameNotFoundException.class);
        
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(EMAIL);
        });
    }
}