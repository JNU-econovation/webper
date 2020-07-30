package econo.webper.server.component;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class Component {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private Integer directoryId;

    private ComponentCategory category;

    public Component(String title, Integer directoryId, ComponentCategory category) {
        this.title = title;
        this.directoryId = directoryId;
        this.category = category;
    }

    public boolean isSameId(Integer id) {
        return this.id == id;
    }

    public boolean update(Object updateDTO) {
        return false;
    }

    protected void updateTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }
}
