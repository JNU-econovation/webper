package econo.webper.server.security.jwt;

import econo.webper.server.member.MemberRole;
import econo.webper.server.security.User;
import econo.webper.server.security.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JwtTokenProvider.class})
@PropertySource(value = "classpath:application.properties")
public class JwtTokenProviderTest {
    
    private static final Integer ID = 1;
    private static final String EMAIL = "user@email.com";
    private static final String PASSWORD = "pass";
    private static final String TOKEN = "token value";
    
    @MockBean
    UserService userService;
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Value("spring.jwt.secret")
    private String secretKey;
    
    @DisplayName("Email을 통한 Token생성 테스트")
    @Test
    public void createTokenTest() {
        String jwtToken = jwtTokenProvider.createToken(EMAIL, Collections.singletonList(MemberRole.USER));
        
        assertThat(jwtTokenProvider.getUserPk(jwtToken)).isEqualTo(EMAIL);
    }
    
    @DisplayName("JWT Token을 활용한 Authentication 객체 생성 테스트")
    @Test
    public void getAuthenticationTest() {
        String jwtToken = jwtTokenProvider.createToken(EMAIL, Collections.singletonList(MemberRole.USER));
        User user = new User(ID, EMAIL, PASSWORD, Collections.singletonList(MemberRole.USER));
        
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
        User principal = (User) authentication.getPrincipal();
        
        assertThat(principal).isEqualTo(user);
    }
    
    @DisplayName("JWT Token에서 Email 추출 테스트")
    @Test
    public void getUserPkTest() {
        String jwtToken = jwtTokenProvider.createToken(EMAIL, Collections.singletonList(MemberRole.USER));
        
        assertThat(jwtTokenProvider.getUserPk(jwtToken)).isEqualTo(EMAIL);
    }
    
    @DisplayName("HTTP Request Header에서 Authorization Token추출 테스트")
    @Test
    public void resolveTokenTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", TOKEN);
        
        assertThat(jwtTokenProvider.resolveToken(request)).isEqualTo(TOKEN);
    }
    
    @DisplayName("생성된 JWT Token이 유효할 경 테스트")
    @Test
    public void validatedTokenTest() {
        String jwtToken = jwtTokenProvider.createToken(EMAIL, Collections.singletonList(MemberRole.USER));
        
        assertThat(jwtTokenProvider.validateToken(jwtToken)).isTrue();
    }
    
    @DisplayName("생성된 JWT token이 유효하지 않을 경우 테스트")
    @Test
    public void unvalidatedTokenTest() {
        Claims claims = Jwts.claims().setSubject(EMAIL);
        claims.put("roles", Collections.singletonList(MemberRole.USER));
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