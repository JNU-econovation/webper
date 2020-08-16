package econo.webper.server.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    private static final String FIRST_MEMBER_EMAIL = "a@a";
    private static final String FIRST_PASSWORD = "1234";
    private static final String FIRST_NAME = "name";
    private static final String THIRD_EMAIL = "c@c";

    private static final int FIRST_INDEX = 0;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void setup() {
        Member member1 = Member.builder()
                .email(FIRST_MEMBER_EMAIL)
                .password(FIRST_PASSWORD)
                .name(FIRST_NAME)
                .roles(Collections.singletonList(MemberRole.USER))
                .build();
        memberRepository.save(member1);
    }

    @DisplayName("저장된 멤버 검색 테스트")
    @Test
    public void findByEmailTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail(FIRST_MEMBER_EMAIL);
        Member member = optionalMember.get();

        assertThat(optionalMember.isPresent()).isTrue();
        assertThat(member.getEmail()).isEqualTo(FIRST_MEMBER_EMAIL);
        assertThat(member.getPassword()).isEqualTo(FIRST_PASSWORD);
        assertThat(member.getName()).isEqualTo(FIRST_NAME);
        assertThat(member.getRoles().get(FIRST_INDEX)).isEqualTo(MemberRole.USER);

        memberRepository.deleteAll();
    }

    @DisplayName("저장되지 않은 멤버 검색시 테스트")
    @Test
    public void findNotExistMemberByEmailTest() {
        Optional<Member> optionalMember = memberRepository.findByEmail(THIRD_EMAIL);

        assertThat(optionalMember.isPresent()).isFalse();

        memberRepository.deleteAll();
    }


}