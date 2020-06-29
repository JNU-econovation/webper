package econo.webper.server.domain;

import econo.webper.server.login.GoogleUserinfoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void saveUserTest() {
        // Given
        String email = "jongjin@email.com";
        String name = "jongjin";
        GoogleUserinfoDTO googleUserinfoDTO = new GoogleUserinfoDTO();
        googleUserinfoDTO.setEmail(email);
        googleUserinfoDTO.setName(name);
        userService.saveUser(googleUserinfoDTO, Collections.singletonList(UserRole.USER));

        // When
        User user = userService.findUserByEmail(googleUserinfoDTO.getEmail());


        // Then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);

    }

}