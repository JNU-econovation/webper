package econo.webper.server.component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Component {
    @Id
    @GeneratedValue
    private Integer id;
}
