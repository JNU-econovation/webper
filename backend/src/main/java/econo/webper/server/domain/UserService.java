package econo.webper.server.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import econo.webper.server.login.GoogleUserinfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(GoogleUserinfoDTO googleUserinfoDTO) {
        User user = User.builder()
                .email(googleUserinfoDTO.getEmail())
                .name(googleUserinfoDTO.getName())
                .build();
        return userRepository.save(user);
    }


}
