package econo.webper.server.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = "id")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String email;

    private String password;

    @NotNull
    private String name;

    private String introduction;

}

