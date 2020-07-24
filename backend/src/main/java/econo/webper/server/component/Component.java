package econo.webper.server.component;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Component {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

}
