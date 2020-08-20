package econo.webper.server.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GoogleUserinfoDTO {

    private String email;

    public GoogleUserinfoDTO() {
    }

    public GoogleUserinfoDTO(String email) {
        this.email = email;
    }
}
