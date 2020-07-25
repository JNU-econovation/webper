package econo.webper.server.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @NoArgsConstructor @Getter
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

    public String objectToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }



}
