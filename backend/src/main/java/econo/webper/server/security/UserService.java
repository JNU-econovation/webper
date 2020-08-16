package econo.webper.server.security;

import econo.webper.server.member.Member;
import econo.webper.server.member.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final MemberService memberService;

    public UserService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findMemberByEmail(email);
        return User.builder()
                .id(member.getId())
                .email(member.getPassword())
                .password(member.getPassword())
                .role(member.getRoles())
                .build();
    }
}
