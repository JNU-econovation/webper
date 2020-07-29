package econo.webper.server.component;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
public class Blog extends Component {

    private String thumbnailURL;

    private String description;

    private String redirectionLink;

    public Blog(String title, Integer directoryId, ComponentCategory componentCategory, String thumbnailURL, String description, String redirectionLink) {
        super(title, directoryId, componentCategory);
        this.thumbnailURL = thumbnailURL;
        this.description = description;
        this.redirectionLink = redirectionLink;
    }

}
