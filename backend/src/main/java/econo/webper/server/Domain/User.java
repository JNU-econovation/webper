package econo.webper.server.Domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter @Builder @NoArgsConstructor
@EqualsAndHashCode(of = "id")
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

