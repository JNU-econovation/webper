package econo.webper.server.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String introduction;

}

