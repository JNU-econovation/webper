package econo.webper.server.Member;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.login.GoogleUserinfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MemberService {

    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(GoogleUserinfoDTO googleUserinfoDTO, List<MemberRole> memberRoles) {
        Optional<Member> savedMember = memberRepository.findByEmail(googleUserinfoDTO.getEmail());
        if(savedMember.isPresent()) {
            return savedMember.get();
        }
        Member member = Member.builder()
                .email(googleUserinfoDTO.getEmail())
                .roles(memberRoles)
                .build();
        return memberRepository.save(member);
    }

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
    }



}
