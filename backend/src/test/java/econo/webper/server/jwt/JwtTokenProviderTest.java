package econo.webper.server.jwt;

import econo.webper.server.domain.User;
import econo.webper.server.domain.UserRole;
import econo.webper.server.domain.UserUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenProviderTest {

    @MockBean
    UserUserDetailsService userUserDetailsService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Value("spring.jwt.secret")
    private String secretKey;


    @Test
    public void createTokenTest() {
        String jwtToken = jwtTokenProvider.createToken("JongJin", Collections.singletonList(UserRole.USER));

        assertThat(jwtTokenProvider.getUserPk(jwtToken)).isEqualTo("JongJin");
    }

    @Test
    public void getAuthenticationTokenTest() {
        // Given
        String jwtToken = jwtTokenProvider.createToken("JongJin", Collections.singletonList(UserRole.USER));
        User user = new User();

        // When
        when(userUserDetailsService.loadUserByUsername("JongJin")).thenReturn(user);
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);

        //Then
        User principal = (User) authentication.getPrincipal();
        assertThat(principal).isEqualTo(user);
    }

    @Test
    public void getUserPkTest() {
        String jwtToken = jwtTokenProvider.createToken("JongJin", Collections.singletonList(UserRole.USER));
        assertThat(jwtTokenProvider.getUserPk(jwtToken)).isEqualTo("JongJin");
    }

    @Test
    public void resolveTokenTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization","Token");
        assertThat(jwtTokenProvider.resolveToken(request)).isEqualTo("Token");
    }

    @Test
    public void validatedTokenTest() {
        String jwtToken = jwtTokenProvider.createToken("JongJin", Collections.singletonList(UserRole.USER));

        assertThat(jwtTokenProvider.validateToken(jwtToken)).isTrue();
    }

    @Test
    public void unvalidatedTokenTest() {
        Claims claims = Jwts.claims().setSubject("JongJin");
        claims.put("roles", Collections.singletonList(UserRole.USER));
        Date now = new Date();
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() - 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        assertThat(jwtTokenProvider.validateToken(jwtToken)).isFalse();
    }
}